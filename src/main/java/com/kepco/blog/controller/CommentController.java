package com.kepco.blog.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kepco.blog.model.Account;
import com.kepco.blog.model.Comment;
import com.kepco.blog.repository.BoardRepository;
import com.kepco.blog.repository.CommentRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class CommentController {
    @Autowired
    HttpSession session;
    @Autowired
    BoardRepository boardDB;
    @Autowired
    CommentRepository commentDB;

    @GetMapping("/blog/board/{postNumber}/{commNumber}")
    public String deleteComment(@PathVariable String postNumber, @PathVariable String commNumber) {
        Comment selectedComment = commentDB.findByCommNumber(Integer.parseInt(commNumber));

        commentDB.delete(selectedComment);

        return "redirect:/blog/board/"+postNumber;
    }
    @PostMapping("/blog/board/commWrite")
    public String createComment(@RequestParam String content, @RequestParam String postNumber) {
        Comment newComment = new Comment();

        newComment.setAccount((Account)session.getAttribute("user"));
        newComment.setContent(content);
        newComment.setBoard(boardDB.findByPostNumber(Integer.parseInt(postNumber)));
        newComment.setCreationDate(LocalDate.now().toString());
        commentDB.save(newComment);

        return "redirect:/blog/board/"+postNumber;
    }
}
