package com.sparta.hanghaeboard.entity;

import com.sparta.hanghaeboard.dto.BoardRequestDto;
import com.sparta.hanghaeboard.dto.MemberRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 10)
    private String username;

    @Column(nullable = false, length = 15)
    private String password;

    public Member(MemberRequestDto memberRequestDto) {
        this.username = memberRequestDto.getUsername();
        this.password = memberRequestDto.getPassword();
    }


}
