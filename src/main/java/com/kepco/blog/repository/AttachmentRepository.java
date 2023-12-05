package com.kepco.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kepco.blog.model.Attachment;
import com.kepco.blog.model.Board;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment,String> {
    Attachment findByUid(String uid);
    List<Attachment> findByBoard(Board board);
}