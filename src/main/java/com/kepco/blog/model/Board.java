package com.kepco.blog.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int postNumber;
    @ManyToOne
    @JoinColumn(name="user_id")
    private Account account;
    private String title;
    private String content;
    private int viewCount;
    private String creationDate;
    @OneToMany(mappedBy="board", cascade=CascadeType.REMOVE)
    @JsonIgnore
    private List<Attachment> attachments = new ArrayList<>();
    @OneToMany(mappedBy="board", cascade=CascadeType.REMOVE)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();
}
