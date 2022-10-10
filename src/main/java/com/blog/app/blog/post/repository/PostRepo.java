package com.blog.app.blog.post.repository;

import com.blog.app.blog.category.entity.Category;
import com.blog.app.blog.post.entity.Post;
import com.blog.app.blog.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);
}
