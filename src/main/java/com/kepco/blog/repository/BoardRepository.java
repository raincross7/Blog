package com.kepco.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kepco.blog.model.Account;
import com.kepco.blog.model.Board;
import java.util.List;


@Repository
public interface BoardRepository extends JpaRepository<Board,Integer> {
    Board findByPostNumber(int postNumber);
    List<Board> findByAccount(Account account);
    List<Board> findByTitleContaining(String sentence);
    List<Board> findByContentContaining(String sentence);
}
