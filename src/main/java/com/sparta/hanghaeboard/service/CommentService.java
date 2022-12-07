package com.sparta.hanghaeboard.service;

import com.sparta.hanghaeboard.dto.BoardResponseDto;
import com.sparta.hanghaeboard.dto.CommentRequestDto;
import com.sparta.hanghaeboard.dto.CommentResponseDto;
import com.sparta.hanghaeboard.dto.ResponseDto;
import com.sparta.hanghaeboard.entity.Board;
import com.sparta.hanghaeboard.entity.Comment;
import com.sparta.hanghaeboard.entity.Member;
import com.sparta.hanghaeboard.entity.UserRoleEnum;
import com.sparta.hanghaeboard.jwt.JwtUtil;
import com.sparta.hanghaeboard.repository.BoardRepository;
import com.sparta.hanghaeboard.repository.CommentRepository;
import com.sparta.hanghaeboard.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;

    public CommentResponseDto comment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        //1. 토큰 확인
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            //Token 검증 (위변조)
            if (jwtUtil.validateToken(token)){
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);

                // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회 - JwtUtil 에서 토큰 생성시 setSubject(username) 했기 때문에 getSubject()
                Member member = memberRepository.findByUsername(claims.getSubject()).orElseThrow(
                        () -> new IllegalArgumentException("존재하지 않는 회원입니다.")
                );

                // 해당 사용자 권한 확인
                //String role = memberRepository.find

                //2. 글 유무 확인
                Board board = boardRepository.findById(id).orElseThrow(
                        () -> new IllegalArgumentException("존재하지 않는 글 입니다.")
                );

                //3. 댓글 작성
                Comment comment = new Comment(board,member,commentRequestDto.getContent());
                commentRepository.save(comment);

                return new CommentResponseDto(comment);

            } else {
                throw new IllegalArgumentException("Token Error");
            }

        } else {
            throw new IllegalArgumentException("로그인 후 댓글 작성 가능합니다.");
        }
    }

    public CommentResponseDto commentUpdate(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        //1. 토큰 확인
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            //Token 검증 (위변조)
            if (jwtUtil.validateToken(token)){

                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);

                // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회 - JwtUtil 에서 토큰 생성시 setSubject(username) 했기 때문에 getSubject()
                Member member = memberRepository.findByUsername(claims.getSubject()).orElseThrow(
                        () -> new IllegalArgumentException("존재하지 않는 회원입니다.")
                );

                //2. 댓글 유무 확인
                Comment comment = commentRepository.findById(id).orElseThrow(
                        () -> new IllegalArgumentException("존재하지 않는 댓글 입니다.")
                );

                System.out.println("member.getRole().equals(\"ADMIN\") : " + member.getRole().equals("ADMIN"));
                String role = String.valueOf(member.getRole().equals("ADMIN"));
                System.out.println("role.equals(\"ADMIN\") : " + role.equals("ADMIN"));
                //다 false로 나오는 이유는 Enum 타입과 String 타입과 비교하니 값이 같은 것 같아도 false 가 나오는 것

                //3. 댓글 수정 권한 조회(admin, 댓글 작성자)
                if (member.getRole().equals(UserRoleEnum.ADMIN)|| member.getUsername().equals(comment.getMember().getUsername())) {

                    //4. 댓글 수정
                    comment.update(commentRequestDto);
                    return new CommentResponseDto(comment);

                } else {
                    throw new IllegalArgumentException("관리자 또는 댓글 작성자만 수정 가능합니다.");
                }

            } else {
                throw new IllegalArgumentException("Token Error");
            }

        } else {
            throw new IllegalArgumentException("로그인 후 댓글 작성 가능합니다.");
        }
    }

    public ResponseDto commentDelete(Long id, HttpServletRequest request) {
        //1. 토큰 확인
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            //Token 검증 (위변조)
            if (jwtUtil.validateToken(token)){

                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);

                // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회 - JwtUtil 에서 토큰 생성시 setSubject(username) 했기 때문에 getSubject()
                Member member = memberRepository.findByUsername(claims.getSubject()).orElseThrow(
                        () -> new IllegalArgumentException("존재하지 않는 회원입니다.")
                );

                //2. 댓글 유무 확인
                Comment comment = commentRepository.findById(id).orElseThrow(
                        () -> new IllegalArgumentException("존재하지 않는 댓글 입니다.")
                );

                //3. 댓글 삭제 권한 조회(admin, 댓글 작성자)
                if (member.getRole().equals(UserRoleEnum.ADMIN)|| member.getUsername().equals(comment.getMember().getUsername())) {

                    //4. 댓글 삭제
                    commentRepository.delete(comment);
                    return new ResponseDto("댓글 삭제 완료", HttpStatus.OK.value());

                } else {
                    throw new IllegalArgumentException("관리자 또는 댓글 작성자만 수정 가능합니다.");
                }

            } else {
                throw new IllegalArgumentException("Token Error");
            }

        } else {
            throw new IllegalArgumentException("로그인 후 댓글 작성 가능합니다.");
        }

    }
}
