package com.sparta.hanghaeboard.controller;

import com.sparta.hanghaeboard.dto.BoardListResponseDto;
import com.sparta.hanghaeboard.dto.BoardRequestDto;
import com.sparta.hanghaeboard.dto.BoardResponseDto;
import com.sparta.hanghaeboard.dto.ResponseDto;
import com.sparta.hanghaeboard.entity.Board;
import com.sparta.hanghaeboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
//Json 형태로 객체 데이터를 반환하는 것
//@Controller에 @ReponseBody를 붙인 것과 완벽히 동일
//매번 API 마다 @ReponseBody 붙이기 귀찮으니까 @RestController 사용하면 편함
@RequestMapping("/api")
@RequiredArgsConstructor
//클래스에 선언된 final 변수들, @NotNull 필드들을 매개변수로 하는 생성자를 자동으로 생성해주는 어노테이션
//이를 통해 새로운 필드를 추가할 때 다시 생성자를 만들거나 하는 등의 번거로움을 없앨 수 있다.
public class BoardController {
    private final BoardService boardService;

    //Rest Api 관례 찾아보기, url 주소에 동사를 안쓰는게 좋음
    //게시글 작성
    @PostMapping("/boardWrite")
    public BoardResponseDto boardWrite(@RequestBody BoardRequestDto boardRequestDto) {
        return boardService.boardWrite(boardRequestDto);
    }

    //게시물 전체 조회
    @GetMapping("/boradList")
    public BoardListResponseDto getBoardList() {
        return boardService.getBoardList();
    }

    //게시물 상세 조회
    @GetMapping("/boardDetail") //파라미터 값을 가져올 땐 @RequestParam
    public BoardResponseDto getBoardDetail(@RequestParam Long id) {
        return boardService.getBoardDetail(id);
    }

    //게시물 수정
    @PutMapping("/boardUpdate/{id}")
    public BoardResponseDto boardUpdate(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto) {
        return boardService.boardUpdate(id, boardRequestDto);
    }

    //게시물 삭제
    @DeleteMapping("/boardDelete/{id}")
    public ResponseDto boardDelete(@PathVariable Long id, @RequestParam Long password) {
        return boardService.boardDelete(id,password);
    }



}
