package com.sparta.hanghaeboard.dto;

import com.sparta.hanghaeboard.entity.Board;
import com.sparta.hanghaeboard.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDto extends ResponseDto{
    private Long id;
    private String title;
    //private String writer;
    private String username;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public BoardResponseDto(String msg, int statusCode) {
        super(msg, statusCode);
    }

    public BoardResponseDto(Board board, Member member) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.username = member.getUsername();
        this.contents = board.getContents();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.username = board.getMember().getUsername();
        this.contents = board.getContents();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}
