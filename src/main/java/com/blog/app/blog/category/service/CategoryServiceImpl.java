package com.blog.app.blog.category.service;

import com.blog.app.blog.category.entity.Category;
import com.blog.app.blog.category.payload.CategoryDto;
import com.blog.app.blog.category.repository.CategoryRepo;
import com.blog.app.blog.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.dtoToCategory(categoryDto);
        Category savedCategory = this.categoryRepo.save(category);
        return this.categoryToDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","id",Integer.toString(categoryId)));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        Category savedCategory = this.categoryRepo.save(category);
        return this.categoryToDto(savedCategory);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","id",Integer.toString(categoryId)));
        return this.categoryToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map(category -> this.categoryToDto(category)).collect(Collectors.toList());
        return categoryDtos;
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","id",Integer.toString(categoryId)));
        this.categoryRepo.delete(category);
    }

    private Category dtoToCategory(CategoryDto categoryDto){
        Category category = this.modelMapper.map(categoryDto, Category.class);
        return category;
    }

    private CategoryDto categoryToDto(Category category){
        CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }
}
