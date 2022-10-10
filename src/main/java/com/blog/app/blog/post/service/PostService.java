package com.blog.app.blog.post.service;

import com.blog.app.blog.post.payload.PostDto;
import com.blog.app.blog.utils.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    PostDto updatePost(PostDto postDto, Integer postId);

    void deletePost(Integer id);

    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    PostDto getAllPostById(Integer postId);

    List<PostDto> getPostsByCategory(Integer categoryId);

    List<PostDto> getPostsByUser(Integer userId);

    List<PostDto> searchPosts(String keyword);

}
