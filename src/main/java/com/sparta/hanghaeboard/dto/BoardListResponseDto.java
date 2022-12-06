package com.sparta.hanghaeboard.dto;

import com.sparta.hanghaeboard.entity.Board;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BoardListResponseDto extends ResponseDto{
    //ResponseDto와 boardList는 같은 선상에 있고
    //boardList 배열 값들은 안쪽에 있는 형태로 보여짐
    List<BoardResponseDto> boardList = new ArrayList<>();

    public void addBoardList(BoardResponseDto boardResponseDto) {
        boardList.add(boardResponseDto);
    }

    public BoardListResponseDto() {
        super("게시글 목록 조회 성공", HttpStatus.OK.value());
        //super() : 부모 클래스의 생성자 호출
    }

}
