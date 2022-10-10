package com.blog.app.blog.category.repository;

import com.blog.app.blog.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
}
