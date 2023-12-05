package com.kepco.blog.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kepco.blog.model.Account;
import com.kepco.blog.model.Attachment;
import com.kepco.blog.model.Board;
import com.kepco.blog.model.Comment;
import com.kepco.blog.repository.AttachmentRepository;
import com.kepco.blog.repository.BoardRepository;
import com.kepco.blog.repository.CommentRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class BoardController {
    @Autowired
    HttpSession session;
    @Autowired
    BoardRepository boardDB;
    @Autowired
    AttachmentRepository attachmentDB;
    @Autowired
    CommentRepository commentDB;

    @GetMapping("/blog/board")
    public String printAllContent(Model model) {
        List<Board> allContent = boardDB.findAll();

        model.addAttribute("contents", allContent);

        return "board";
    }
    @GetMapping("/blog/board/{postNumber}")
    public String printOneContent(@PathVariable int postNumber, Model model) {
        Board selectedContent = boardDB.findByPostNumber(postNumber);
        List<Attachment> selectedAttachments = attachmentDB.findByBoard(selectedContent);
        List<Comment> selectedComments = commentDB.findByBoard(selectedContent);

        selectedContent.setViewCount(selectedContent.getViewCount()+1);
        boardDB.save(selectedContent);
        model.addAttribute("content", selectedContent);
        model.addAttribute("files", selectedAttachments);
        model.addAttribute("comments", selectedComments);
        
        return "content";
    }
    @GetMapping("/blog/board/search")
    public String printSearchContent(@RequestParam String type, @RequestParam String search, Model model) {
        if ("title".equals(type)) {
            List<Board> selectedContents = boardDB.findByTitleContaining(search);
            model.addAttribute("contents", selectedContents);
        }
        else if ("content".equals(type)) {
            List<Board> selectedContents = boardDB.findByContentContaining(search);
            model.addAttribute("contents", selectedContents);
        }

        return "board";
    }
    @GetMapping("/blog/board/write")
    public String createContentPage() {
        return "writeContent";
    }
    @GetMapping("/blog/board/rewrite")
    public String editContentPage(@RequestParam("rewrite") int postNumber, Model model) {
        Board selectedContent = boardDB.findByPostNumber(postNumber);

        model.addAttribute("content", selectedContent);

        return "rewriteContent";
    }
    @GetMapping("/blog/board/delete")
    public String deleteContent(@RequestParam("delete") int postNumber) {
        Board selectedBoard = boardDB.findByPostNumber(postNumber);
        List<Attachment> selectedAttachments = attachmentDB.findByBoard(selectedBoard);
        
        for (Attachment selectedAttachment : selectedAttachments) {
            File selectedFile = new File("C:/blog/src/main/resources/static/files/"+selectedAttachment.getUid()+'_'+selectedAttachment.getFileName());

            selectedFile.delete();
        }
        boardDB.delete(selectedBoard);

        return "redirect:/blog/board";
    }
    @PostMapping("/blog/board/write")
    public String createContent(@RequestParam String userId, @RequestParam String title, @RequestParam String content, MultipartFile mfile, @RequestParam int type) {
        Board newBoard;
        
        if (type == 0) {
            newBoard = new Board();
            newBoard.setAccount((Account)session.getAttribute("user"));
        }
        else {
            newBoard = boardDB.findByPostNumber(type);
        }
        newBoard.setTitle(title);
        newBoard.setContent(content);
        newBoard.setCreationDate(LocalDate.now().toString());
        boardDB.save(newBoard);
        
        if (!mfile.isEmpty()) {
            try {
                Attachment newAttachment = new Attachment();

                newAttachment.setUid(UUID.randomUUID().toString());
                newAttachment.setFileName(mfile.getOriginalFilename());
                newAttachment.setAccount((Account)session.getAttribute("user"));
                newAttachment.setBoard(newBoard);
                newAttachment.setUploadDate(LocalDate.now().toString());
                attachmentDB.save(newAttachment);

                File newFile = new File("C:/blog/src/main/resources/static/files/"+newAttachment.getUid()+'_'+newAttachment.getFileName());
                
                mfile.transferTo(newFile);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }

        return "redirect:/blog/board";
    }
}
