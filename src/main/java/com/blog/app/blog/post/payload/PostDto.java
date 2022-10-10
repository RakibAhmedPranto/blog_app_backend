package com.blog.app.blog.post.payload;

import com.blog.app.blog.category.payload.CategoryDto;
import com.blog.app.blog.comment.payload.CommentDto;
import com.blog.app.blog.user.payload.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
    private Integer id;
    private String title;
    private String content;
    private String imageName;
    private Date addedDate;

    private CategoryDto category;
    private UserDto user;
    private Set<CommentDto> comments = new HashSet<>();
}
