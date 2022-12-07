package com.sparta.hanghaeboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequestDto {
    private String username;
    private String password;
    private boolean admin = false; //회원가입시 관리자 체크 : true, 아니면 false
    private String adminToken = "";
}
