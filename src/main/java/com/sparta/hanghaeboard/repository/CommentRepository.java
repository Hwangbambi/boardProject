package com.sparta.hanghaeboard.repository;

import com.sparta.hanghaeboard.entity.Board;
import com.sparta.hanghaeboard.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
