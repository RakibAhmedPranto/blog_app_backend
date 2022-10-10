package com.blog.app.blog.email.service;

import com.blog.app.blog.exceptions.MailSendFailedException;

import javax.mail.MessagingException;

public interface EmailService {
    boolean sendEmail(String subject,String message, String to) throws MessagingException;
}
