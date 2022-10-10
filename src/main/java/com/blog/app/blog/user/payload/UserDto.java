package com.blog.app.blog.user.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Integer id;
    @NotEmpty
    @Size(min = 4, message = "User Name Must be min 4 character long")
    private String name;
    @Email(message = "Email is not valid")
    @NotEmpty
    private String email;
    @NotEmpty
    @Size(min = 8,message = "Password Must be at least 8 character long ")
    private String password;
    @NotEmpty
    private String about;
}
