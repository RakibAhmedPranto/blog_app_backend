package com.blog.app.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernameOrPasswordMismatchedException extends RuntimeException{
    String message;

    public UsernameOrPasswordMismatchedException(String message){
        this.message = message;
    }
}
