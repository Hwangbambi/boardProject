package com.sparta.hanghaeboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//테이블에 값을 넣을 때 Board 클래스를 직접 사용하는 것은 좋지 않다.
//그래서 완충재로 활용하는 것이 DTO
public class BoardRequestDto {
    private String title;
    private String writer;
    private String contents;
    private Long password;
}
