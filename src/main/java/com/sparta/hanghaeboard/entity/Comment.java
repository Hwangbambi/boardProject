package com.sparta.hanghaeboard.entity;

import com.sparta.hanghaeboard.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne() //fetch 알아보기
    @JoinColumn(name = "userId", nullable = false) //Member Id 와 join
    private Member member;

    @ManyToOne
    @JoinColumn(name = "boardId", nullable = false) //Board Id 와 join
    private Board board;

    public Comment(Board board, Member member, String content) {
        this.content = content;
        this.member = member;
        this.board = board;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }
}
