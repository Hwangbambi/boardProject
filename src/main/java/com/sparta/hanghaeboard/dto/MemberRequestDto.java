package com.sparta.hanghaeboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class MemberRequestDto {
    private String username;
    private String password;
}
