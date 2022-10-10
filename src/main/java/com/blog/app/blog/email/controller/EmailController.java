package com.blog.app.blog.email.controller;

import com.blog.app.blog.email.payload.EmailRequest;
import com.blog.app.blog.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody EmailRequest emailRequest) throws MessagingException {
        boolean res = this.emailService.sendEmail(emailRequest.getSubject(), emailRequest.getMessage(), emailRequest.getTo());
        if(res){
            return ResponseEntity.ok("Email Sent....");
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("email not send");
        }
    };
}
