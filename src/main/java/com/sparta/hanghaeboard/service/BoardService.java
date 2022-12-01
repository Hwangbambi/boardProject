package com.sparta.hanghaeboard.service;

import com.sparta.hanghaeboard.dto.BoardListResponseDto;
import com.sparta.hanghaeboard.dto.BoardRequestDto;
import com.sparta.hanghaeboard.dto.BoardResponseDto;
import com.sparta.hanghaeboard.dto.ResponseDto;
import com.sparta.hanghaeboard.entity.Board;
import com.sparta.hanghaeboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    //BoardRepository 연결되어 사용할 수 있다.

    @Transactional
    public BoardResponseDto boardWrite(BoardRequestDto boardRequestDto) {
        Board board = new Board(boardRequestDto); //db에 저장할 데이터 1 row 을 만들었다 라고 이해
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }

    @Transactional(readOnly = true)
    //데이터 추가, 수정, 삭제 등으로 이루어진 작업을 처리하던 중 오류가 발생했을 때 모든 작업들을 원상태로 되돌릴 수 있다.
    public BoardListResponseDto getBoardList() {
        BoardListResponseDto boardListResponseDto = new BoardListResponseDto();

        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        for (Board board : boardList) {
            boardListResponseDto.addBoardList(new BoardResponseDto(board));
        }
        return boardListResponseDto;
    }

    @Transactional //이거 안쓰면 db에 저장 안됨!!!! 필수임
    public BoardResponseDto getBoardDetail(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("글이 존재하지 않습니다.")
        );
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto boardUpdate(Long id, BoardRequestDto boardRequestDto) {
        //1. id 유무확인
        boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("글이 존재하지 않습니다.")
        );

        //2. id의 pw와 입력받은 pw 비교
        Board board = boardRepository.findByIdAndPassword(id, boardRequestDto.getPassword()).orElseThrow(
                () -> new IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        );
        board.update(boardRequestDto); //boardRepository와 연결한게 없는데 어케 update 되는건지?
        return new BoardResponseDto(board);
    }

    @Transactional
    public ResponseDto boardDelete(Long id, Long password) {
        //1. id 유무확인
        boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("글이 존재하지 않습니다.")
        );

        //2. id의 pw와 입력받은 pw 비교
        boardRepository.findByIdAndPassword(id, password).orElseThrow(
                () -> new IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        );
        boardRepository.deleteById(id);
        return new ResponseDto("글삭제 완료", HttpStatus.OK.value());
    }
}
