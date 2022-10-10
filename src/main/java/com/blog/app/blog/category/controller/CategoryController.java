package com.blog.app.blog.category.controller;

import com.blog.app.blog.category.payload.CategoryDto;
import com.blog.app.blog.category.service.CategoryService;
import com.blog.app.blog.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createdCategory = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto,categoryId);
        return ResponseEntity.ok(updatedCategory);
    }
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        return ResponseEntity.ok(this.categoryService.getAllCategories());
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Integer categoryId){
        return ResponseEntity.ok(this.categoryService.getCategoryById(categoryId));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully",true), HttpStatus.OK);
    }
}
