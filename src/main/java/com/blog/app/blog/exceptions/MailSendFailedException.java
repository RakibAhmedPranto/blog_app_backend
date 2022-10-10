package com.blog.app.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

import javax.mail.SendFailedException;

@Getter
@Setter
public class MailSendFailedException extends SendFailedException {
    String message;

    public MailSendFailedException(String message){
        this.message=message;
    }
}
