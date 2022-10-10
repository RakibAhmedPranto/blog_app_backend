package com.blog.app.blog.post.service;

import com.blog.app.blog.category.entity.Category;
import com.blog.app.blog.category.repository.CategoryRepo;
import com.blog.app.blog.exceptions.ResourceNotFoundException;
import com.blog.app.blog.post.entity.Post;
import com.blog.app.blog.post.payload.PostDto;
import com.blog.app.blog.post.repository.PostRepo;
import com.blog.app.blog.user.entity.User;
import com.blog.app.blog.user.repository.UserRepo;
import com.blog.app.blog.utils.PostResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",Integer.toString(userId)));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","id",Integer.toString(categoryId)));
        Post post = this.dtoToPost(postDto);
        post.setImageName("default.pmg");
        post.setAddedDate(new Date());
        post.setCategory(category);
        post.setUser(user);
        Post savedPost = this.postRepo.save(post);
        return this.postToDto(savedPost);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",Integer.toString(postId)));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post updatedPost = this.postRepo.save(post);
        return this.postToDto(updatedPost);
    }

    @Override
    public void deletePost(Integer id) {
        Post post = this.postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",Integer.toString(id)));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = null;

        if(sortDir.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }
        else {
            sort = Sort.by(sortBy).descending();
        }
        PostResponse postResponse = new PostResponse();
        Pageable p = PageRequest.of(pageNumber,pageSize, sort);
        Page<Post> pagePost = this.postRepo.findAll(p);
        List<Post> posts = pagePost.getContent();
        List<PostDto> postDtos = posts.stream().map((post)-> this.postToDto(post)).collect(Collectors.toList());

        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostDto getAllPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",Integer.toString(postId)));
        return this.postToDto(post);
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","id",Integer.toString(categoryId)));
        List<Post> posts = this.postRepo.findByCategory(category);
        return posts.stream().map((post)-> this.postToDto(post)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",Integer.toString(userId)));
        List<Post> posts = this.postRepo.findByUser(user);
        return posts.stream().map((post)-> this.postToDto(post)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = this.postRepo.findByTitleContaining(keyword);
        return posts.stream().map((post)-> this.postToDto(post)).collect(Collectors.toList());
    }

    private Post dtoToPost(PostDto postDto){
        Post post = this.modelMapper.map(postDto,Post.class);
        return post;
    }

    private PostDto postToDto(Post post){
        PostDto postDto = this.modelMapper.map(post, PostDto.class);
        return postDto;
    }
}
