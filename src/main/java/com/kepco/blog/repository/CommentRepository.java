package com.kepco.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kepco.blog.model.Board;
import com.kepco.blog.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer>{
    Comment findByCommNumber(int commNumber);
    List<Comment> findByBoard(Board Board);
}
