package com.sparta.hanghaeboard.controller;

import com.sparta.hanghaeboard.dto.JoinRequestDto;
import com.sparta.hanghaeboard.dto.LoginRequestDto;
import com.sparta.hanghaeboard.dto.ResponseDto;
import com.sparta.hanghaeboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    //회원가입
    @PostMapping("/join")
    public ResponseDto join(@RequestBody JoinRequestDto joinRequestDto){
        return memberService.join(joinRequestDto);
    }

    //로그인
    @PostMapping("/login")
    public ResponseDto login(@RequestBody LoginRequestDto memberRequestDto, HttpServletResponse response){
        return memberService.login(memberRequestDto,response);
    }


}
