package com.sparta.hanghaeboard.entity;

import com.sparta.hanghaeboard.dto.JoinRequestDto;
import com.sparta.hanghaeboard.dto.LoginRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String username;

    @Column(nullable = false, length = 15)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public Member(JoinRequestDto joinRequestDto, UserRoleEnum role) {
        this.username = joinRequestDto.getUsername();
        this.password = joinRequestDto.getPassword();
        this.role = role;
    }


}
