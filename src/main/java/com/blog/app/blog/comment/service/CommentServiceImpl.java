package com.blog.app.blog.comment.service;
import com.blog.app.blog.comment.entity.Comment;
import com.blog.app.blog.comment.payload.CommentDto;
import com.blog.app.blog.comment.repository.CommentRepo;
import com.blog.app.blog.exceptions.ResourceNotFoundException;
import com.blog.app.blog.post.entity.Post;
import com.blog.app.blog.post.repository.PostRepo;
import com.blog.app.blog.user.entity.User;
import com.blog.app.blog.user.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",Integer.toString(postId)));
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",Integer.toString(userId)));
        Comment comment = this.dtoToComment(commentDto);
        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment = this.commentRepo.save(comment);
        return this.commentToDto(savedComment);
    }

    @Override
    public void deleteComment(Integer id) {
        Comment comment = this.commentRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Comment","id",Integer.toString(id)));
        this.commentRepo.delete(comment);
    }

    @Override
    public List<CommentDto> getCommentsByPost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",Integer.toString(postId)));
        List<Comment> comments = this.commentRepo.findByPost(post);
        return comments.stream().map((comment)->this.commentToDto(comment)).collect(Collectors.toList());
    }

    private Comment dtoToComment(CommentDto commentDto){
        Comment comment = this.modelMapper.map(commentDto,Comment.class);
        return comment;
    }

    private CommentDto commentToDto(Comment comment){
        CommentDto commentDto = this.modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }
}
