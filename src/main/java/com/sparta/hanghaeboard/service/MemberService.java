package com.sparta.hanghaeboard.service;

import com.sparta.hanghaeboard.dto.JoinRequestDto;
import com.sparta.hanghaeboard.dto.LoginRequestDto;
import com.sparta.hanghaeboard.dto.ResponseDto;
import com.sparta.hanghaeboard.entity.Member;
import com.sparta.hanghaeboard.entity.UserRoleEnum;
import com.sparta.hanghaeboard.jwt.JwtUtil;
import com.sparta.hanghaeboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    //관리자 회원가입시 관리자 토큰 비교
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseDto join(JoinRequestDto joinRequestDto) {
        //비밀번호 암호화
        joinRequestDto.setPassword(passwordEncoder.encode(joinRequestDto.getPassword()));

        System.out.println(joinRequestDto.getUsername());
        System.out.println(joinRequestDto.getPassword());

        //1. username 중복 유무 확인
        Optional<Member> usernameCheck = memberRepository.findByUsername(joinRequestDto.getUsername());

        if (usernameCheck.isPresent()){
            //throw new IllegalArgumentException("중복된 username 입니다.");
            //이것만 안됨!!!!! 꼭 확ㅇㅣㄴ해..
            return new ResponseDto("중복된 username 입니다.", HttpStatus.BAD_REQUEST.value());
        }

        //2. 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;

        if (joinRequestDto.isAdmin()) {
            // 어드민 토큰 값 확인
            if (ADMIN_TOKEN.equals(joinRequestDto.getAdminToken())) {
                role = UserRoleEnum.ADMIN;

            } else {
                throw new IllegalArgumentException("관리자 토큰이 일치하지 않습니다.");
            }
        }

        //2. DB에 회원 정보 저장
        Member member = new Member(joinRequestDto,role);
        memberRepository.save(member);

        return new ResponseDto("회원가입 되었습니다.", HttpStatus.OK.value());
    }

    @Transactional(readOnly = true) //입력, 수정, 삭제 이외엔 readOnly 하는게 좋음, 최적화
    public ResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        //1. username 유무 확인
        Member member = memberRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        //2. pw 일치 확인
        if (!passwordEncoder.matches(loginRequestDto.getPassword(),member.getPassword())) {
            return new ResponseDto("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST.value());
        }

        //토큰 생성 후 response 에 추가 (name, value)
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member.getUsername(), member.getRole()));

        return new ResponseDto("로그인 되었습니다.", HttpStatus.OK.value());
    }

    @Transactional(readOnly = true)
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        /* 유효성 검사에 실패한 필드 목록을 받음 */
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }
}
