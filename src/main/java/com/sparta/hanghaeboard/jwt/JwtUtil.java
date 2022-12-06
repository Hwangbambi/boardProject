package com.sparta.hanghaeboard.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {
    //Header Name(key)
    public static final String AUTHORIZATION_HEADER = "Authorization";

    //사용자 권한 값의 key ex) 사용자 : user, 관리자 : admin
    public static final String AUTHORIZATION_KEY = "auth";

    //Token 식별자 ex) Bearer eyJhbGciOiJIUzI1NiJ9...
    private static final String BEARER_PREFIX = "Bearer ";

    //토큰 만료시간 /60 * 1000L = 1분 * 60 = 1시간
    private static final long TOKEN_TIME = 60 * 60 * 1000L;

    @Value("${jwt.secret.key}") //application.properties 에 넣은 jwt.secret.key 값 가져오는 코드
    private String secretKey; // = ${jwt.secret.key}
    private Key key; //Token을 만들때 넣어줄 key 값 (Value)
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; //어떤 알고리즘 방식으로 암호화 할건지? HS256

    //객체의 라이프 사이클 초기화 및 종료해주는 어노테이션 즉 객체가 생성될 때마다 초기화 시켜주는 역할 하는 거 같음
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // header 토큰 가져오기
    public String resolveToken(HttpServletRequest request) {
        //Header에서 Name(key)이 Authorization 인 value를 가져오겠다
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        //Header value 값이 있는지, value 값에 Bearer 문자로 시작 되어있는지 확인
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7); // 'Bearer ' 문자는 토큰과 연관이 없는 문자라서 해당 문자 자르고 return
        }
        return null;
    }

    // 토큰 생성
    public String createToken(String username) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, "user")
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) //토큰 유효기간 : 현재 시간 + 1시간
                        .setIssuedAt(date) //토큰 생성일
                        .signWith(key, signatureAlgorithm) //어떤 알고리즘으로 암호화 할 건지
                        .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token); //받아온 토큰(token)과 key 와 비교(검증)
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    // 토큰 검증 코드와 동일한테 getBody() 를 통해서 토근의 정보를 가져올 수 있음
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

}
