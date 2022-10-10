package com.blog.app.blog.email.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender mailSender;
    @Override
    public boolean sendEmail(String subject, String message, String to) throws MessagingException {
        boolean f = false;

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper m = new MimeMessageHelper(mimeMessage,true);

        try{
            m.setFrom("rakib820@gmail.com");
            m.setTo(to);
            m.setSubject(subject);
            m.setText(message);

            mailSender.send(mimeMessage);
            f=true;
        }catch(MessagingException ex)
        {
            throw new MessagingException(ex.getMessage());
        }

        return f;

    }
}