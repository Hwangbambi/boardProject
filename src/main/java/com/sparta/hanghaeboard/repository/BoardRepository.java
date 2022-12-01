package com.sparta.hanghaeboard.repository;

import com.sparta.hanghaeboard.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//DB의 SQL 역할을 하는 인터페이스
//<Board 클래스, id의 type>
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByCreatedAtDesc();
    Optional<Board> findByIdAndPassword(Long id, Long password);
}
