package com.blog.app.blog.comment.service;

import com.blog.app.blog.comment.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);
    void deleteComment(Integer id);

    List<CommentDto> getCommentsByPost(Integer postId);
}
