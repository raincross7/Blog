package com.kepco.blog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int commNumber;
    @ManyToOne
    @JoinColumn(name="user_id")
    private Account account;
    private String content;
    @ManyToOne
    @JoinColumn(name="post_number")
    private Board board;
    private String creationDate;
}