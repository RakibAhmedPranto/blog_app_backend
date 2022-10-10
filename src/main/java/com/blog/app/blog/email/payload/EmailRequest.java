package com.blog.app.blog.email.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class EmailRequest {
    private String to;
    private String subject;
    private String message;
}
