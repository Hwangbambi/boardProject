package com.sparta.hanghaeboard.controller;

import com.sparta.hanghaeboard.dto.CommentRequestDto;
import com.sparta.hanghaeboard.dto.CommentResponseDto;
import com.sparta.hanghaeboard.dto.ResponseDto;
import com.sparta.hanghaeboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    //댓글 작성
    @PostMapping("/comments/{id}")
    public CommentResponseDto comments(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.comments(id, commentRequestDto, request);
    }

    //댓글 수정
    @PostMapping("/comments-update/{id}")
    public CommentResponseDto commentUpdate(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request){
        return commentService.commentUpdate(id, commentRequestDto, request);
    }

    //댓글 삭제
    @PostMapping("/comments-delete/{id}")
    public ResponseDto commentDelete(@PathVariable Long id, HttpServletRequest request){
        return commentService.commentDelete(id, request);
    }

}
