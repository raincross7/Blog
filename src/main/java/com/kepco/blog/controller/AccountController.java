package com.kepco.blog.controller;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kepco.blog.model.Account;
import com.kepco.blog.model.Attachment;
import com.kepco.blog.model.Board;
import com.kepco.blog.repository.AccountRepository;
import com.kepco.blog.repository.AttachmentRepository;
import com.kepco.blog.repository.BoardRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {
    @Autowired
    private HttpSession session;
    @Autowired
    private AccountRepository accountDB;
    @Autowired
    private BoardRepository boardDB;
    @Autowired
    private AttachmentRepository attachmentDB;

    @GetMapping("/blog")
    public String loginPage() {
        return "login";
    }
    @PostMapping("/blog")
    public String checkAccount(@RequestParam String userId, @RequestParam String password, Model model) {
        Account user = accountDB.findByUserIdAndPassword(userId, password);

        if (user!=null) {
            session.setAttribute("user", user);
            return "redirect:/blog/board";
        }
        else {
            model.addAttribute("noneAccount", "ID 혹은 PW 틀림");
            return "login";
        }
    }
    @GetMapping("/blog/logout")
    public String logOut() {
        session.invalidate();
        return "login";
    }
    @GetMapping("/blog/join")
    public String joinAccountPage() {
        return "joinAccount";
    }
    @PostMapping("/blog/join")
    public String joinAccount(@RequestParam String userId, @RequestParam String password, @RequestParam String name, @RequestParam String birthday, @RequestParam String phoneNumber, @RequestParam String email, Model model) {
        Account checkAccount = accountDB.findByUserId(userId);

        if (checkAccount == null) {
            Account newAccount = new Account();
            
            newAccount.setUserId(userId);
            newAccount.setPassword(password);
            newAccount.setName(name);
            newAccount.setBirthday(birthday);
            newAccount.setPhoneNumber(phoneNumber);
            newAccount.setEmail(email);
            newAccount.setRegistrationDate(LocalDate.now().toString());
            accountDB.save(newAccount);

            return "login";
        }
        else {
            model.addAttribute("duplicateId", "중복된 ID");

            return "joinAccount";
        }
    }
    @GetMapping("/blog/edit")
    public String editAccountPage(Model model) {
        Account selectedAccount = (Account)session.getAttribute("user");

        model.addAttribute("account", selectedAccount);

        return "editAccount";
    }
    @PostMapping("/blog/edit")
    public String editAccount(@RequestParam String password, @RequestParam String name, @RequestParam String birthday, @RequestParam String phoneNumber, @RequestParam String email) {
        Account selectedAccount = (Account)session.getAttribute("user");

        selectedAccount.setPassword(password);
        selectedAccount.setName(name);
        selectedAccount.setBirthday(birthday);
        selectedAccount.setPhoneNumber(phoneNumber);
        selectedAccount.setEmail(email);
        accountDB.save(selectedAccount);

        return "redirect:/blog/board";
    }
    @GetMapping("/blog/withdraw")
    public String withdrawAccount() {
        Account selectedAccount = (Account)session.getAttribute("user");
        List<Board> selectedBoards = boardDB.findByAccount(selectedAccount);
        
        for (Board selectedBoard : selectedBoards) {
            List<Attachment> selectedAttachments = attachmentDB.findByBoard(selectedBoard);
            for (Attachment selectedAttachment : selectedAttachments) {
                File selectedFile = new File("C:/blog/src/main/resources/static/files/"+selectedAttachment.getUid()+'_'+selectedAttachment.getFileName());
                
                selectedFile.delete();
            } 
        }
        accountDB.delete(selectedAccount);

        return "login";
    }
}