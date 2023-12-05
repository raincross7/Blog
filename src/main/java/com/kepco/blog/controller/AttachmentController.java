package com.kepco.blog.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.kepco.blog.model.Attachment;
import com.kepco.blog.repository.AttachmentRepository;

@Controller
public class AttachmentController {
    @Autowired
    AttachmentRepository attachmentDB;

    @GetMapping("/blog/board/{postNumber}/{uid}_{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String postNumber, @PathVariable String uid, @PathVariable String fileName) throws IOException {
        File downFile = new File("C:/blog/src/main/resources/static/files/"+uid+'_'+fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(downFile));

        return ResponseEntity.ok()
                .header("content-disposition", "filename="+URLEncoder.encode(fileName, "utf-8"))
                .contentLength(downFile.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
    @GetMapping("/blog/board/{postNumber}/{uid}_{fileName}/delete")
    public String deleteFile(@PathVariable String postNumber, @PathVariable String uid, @PathVariable String fileName) {
        Attachment selectedAttachment = attachmentDB.findByUid(uid);
        File selectedFile = new File("C:/blog/src/main/resources/static/files/"+uid+'_'+fileName);

        selectedFile.delete();
        attachmentDB.delete(selectedAttachment);

        return "redirect:/blog/board/"+postNumber;
    }
}
