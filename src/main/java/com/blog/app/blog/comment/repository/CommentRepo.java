package com.blog.app.blog.comment.repository;

import com.blog.app.blog.comment.entity.Comment;
import com.blog.app.blog.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
    List<Comment> findByPost(Post post);
}
