package com.sparta.hanghaeboard.controller;

import com.sparta.hanghaeboard.dto.MemberRequestDto;
import com.sparta.hanghaeboard.dto.ResponseDto;
import com.sparta.hanghaeboard.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    //회원가입
    @PostMapping("/join")
    public ResponseDto member(@RequestBody MemberRequestDto memberRequestDto){
        return memberService.join(memberRequestDto);
    }

    //로그인
    @PostMapping("/login")
    public ResponseDto login(@RequestBody MemberRequestDto memberRequestDto,  HttpServletResponse response){
        return memberService.login(memberRequestDto,response);
    }


}
