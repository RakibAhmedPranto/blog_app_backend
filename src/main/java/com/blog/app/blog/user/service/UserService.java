package com.blog.app.blog.user.service;

import com.blog.app.blog.user.payload.UserDto;
import com.blog.app.blog.utils.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface UserService {
    UserDto createUser(UserDto userDto);

    void createUserByExcel(MultipartFile file);
    UserDto updateUser(UserDto userDto, Integer userId);
    UserDto getUserById(Integer userId);
    UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    void deleteUser(Integer userId);

}
