package com.sparta.hanghaeboard.controller;

import com.sparta.hanghaeboard.dto.CommentRequestDto;
import com.sparta.hanghaeboard.dto.CommentResponseDto;
import com.sparta.hanghaeboard.dto.ResponseDto;
import com.sparta.hanghaeboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment/{id}")
    public CommentResponseDto comment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.comment(id, commentRequestDto, request);
    }

    @PostMapping("/commentUpdate/{id}")
    public CommentResponseDto commentUpdate(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request){
        return commentService.commentUpdate(id, commentRequestDto, request);
    }

    @PostMapping("/delete/{id}")
    public ResponseDto commentDelete(@PathVariable Long id, HttpServletRequest request){
        return commentService.commentDelete(id, request);
    }

}
