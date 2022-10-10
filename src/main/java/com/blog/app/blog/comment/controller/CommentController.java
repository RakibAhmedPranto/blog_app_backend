package com.blog.app.blog.comment.controller;

import com.blog.app.blog.comment.payload.CommentDto;
import com.blog.app.blog.comment.service.CommentService;
import com.blog.app.blog.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/user/{userId}/comment")
    public ResponseEntity<CommentDto> createPost(
            @RequestBody CommentDto commentDto,
            @PathVariable Integer postId,
            @PathVariable Integer userId
    ){
        CommentDto createComment = this.commentService.createComment(commentDto,postId,userId);
        return new ResponseEntity<>(createComment, HttpStatus.OK);
    }

    @DeleteMapping("comment/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully",true), HttpStatus.OK);
    }

    @GetMapping("/post/{postId}/comment")
    public ResponseEntity<List<CommentDto>> getCommentByPost(@PathVariable Integer postId){
        List<CommentDto> comments = this.commentService.getCommentsByPost(postId);
        return new ResponseEntity<List<CommentDto>>(comments,HttpStatus.OK);
    }
}
