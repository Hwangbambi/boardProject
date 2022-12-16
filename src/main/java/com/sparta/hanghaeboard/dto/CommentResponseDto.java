package com.sparta.hanghaeboard.dto;

import com.sparta.hanghaeboard.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class CommentResponseDto extends ResponseDto{
    private Long id;
    private String content;
    private String username;
    Long boardId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(String msg, int statusCode) {
        super(msg, statusCode);
    }

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getMember().getUsername();
        this.content = comment.getContent();
        this.boardId = comment.getBoard().getId();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
