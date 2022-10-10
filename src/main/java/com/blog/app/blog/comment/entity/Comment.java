package com.blog.app.blog.comment.entity;

import com.blog.app.blog.post.entity.Post;
import com.blog.app.blog.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.security.PrivateKey;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@Setter
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "comment_content", nullable = false, length = 1000)
    private String content;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;
}
