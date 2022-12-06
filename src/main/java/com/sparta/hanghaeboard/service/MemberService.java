package com.sparta.hanghaeboard.service;

import com.sparta.hanghaeboard.dto.MemberRequestDto;
import com.sparta.hanghaeboard.dto.ResponseDto;
import com.sparta.hanghaeboard.entity.Member;
import com.sparta.hanghaeboard.jwt.JwtUtil;
import com.sparta.hanghaeboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    private final JwtUtil jwtUtil;
    @Transactional
    public ResponseDto join(MemberRequestDto memberRequestDto) {

        //1. username 중복 유무 확인
        Optional<Member> usernameCheck = memberRepository.findByUsername(memberRequestDto.getUsername());

        if (usernameCheck.isPresent()){
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        //2. DB에 회원 정보 저장
        Member member = new Member(memberRequestDto);
        memberRepository.save(member);

        return new ResponseDto("회원가입 되었습니다.", HttpStatus.OK.value());
    }

    @Transactional(readOnly = true) //입력, 수정, 삭제 이외엔 readOnly 하는게 좋음, 최적화
    public ResponseDto login(MemberRequestDto memberRequestDto, HttpServletResponse response) {
        //1. username 유무 확인
        Member member = memberRepository.findByUsername(memberRequestDto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        //2. pw 일치 확인
        if (!member.getPassword().equals(memberRequestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //토큰 생성 후 response 에 추가 (name, value)
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member.getUsername()));

        return new ResponseDto("로그인 되었습니다.", HttpStatus.OK.value());
    }
}
