package com.kepco.blog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Attachment {
    @Id
    private String uid;
    private String fileName;
    @ManyToOne
    @JoinColumn(name="user_id")
    private Account account;
    @ManyToOne
    @JoinColumn(name="post_number")
    private Board board;
    private String uploadDate;
}
