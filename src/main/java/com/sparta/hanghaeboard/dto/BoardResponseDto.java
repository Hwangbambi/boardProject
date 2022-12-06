package com.sparta.hanghaeboard.dto;

import com.sparta.hanghaeboard.entity.Board;
import com.sparta.hanghaeboard.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String title;
    //private String writer;
    private String username;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public BoardResponseDto(Board board, Member member) {
        this.id = board.getId();
        this.title = board.getTitle();
        //this.writer = board.getWriter();
        this.username = member.getUsername();
        this.contents = board.getContents();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}
