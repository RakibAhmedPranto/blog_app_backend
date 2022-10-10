package com.blog.app.blog.user.service;

import com.blog.app.blog.ExcelFileUpload.ExcelHelper;
import com.blog.app.blog.config.AppConstants;
import com.blog.app.blog.exceptions.ResourceNotFoundException;
import com.blog.app.blog.post.entity.Post;
import com.blog.app.blog.role.entity.Role;
import com.blog.app.blog.role.repository.RoleRepo;
import com.blog.app.blog.user.entity.User;
import com.blog.app.blog.user.payload.UserDto;
import com.blog.app.blog.user.repository.UserRepo;
import com.blog.app.blog.utils.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto createUser(UserDto userDto) {

        Role role = this.roleRepo.findById(AppConstants.ROLE_USER).get();
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.getRoles().add(role);
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));

        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public void createUserByExcel(MultipartFile file) {
        Role role = this.roleRepo.findById(AppConstants.ROLE_USER).get();
        try {
            Set<User> users = ExcelHelper.convertExcelToUsers(file.getInputStream());
            List<User> userList = users.stream().map(user -> {
                user.setPassword(this.passwordEncoder.encode(user.getPassword()));
                user.getRoles().add(role);
                return user;
            }).collect(Collectors.toList());
            this.userRepo.saveAll(userList);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",Integer.toString(userId)));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));

        User updatedUser = this.userRepo.save(user);
        return this.userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",Integer.toString(userId)));
        return this.userToDto(user);
    }

    @Override
    public UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = null;

        if(sortDir.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }
        else {
            sort = Sort.by(sortBy).descending();
        }

        UserResponse userResponse = new UserResponse();
        Pageable p = PageRequest.of(pageNumber,pageSize,sort);
        Page<User> pageUser = this.userRepo.findAll(p);
        List<User> users = pageUser.getContent();
        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());


        userResponse.setContent(userDtos);
        userResponse.setPageNumber(pageUser.getNumber());
        userResponse.setPageSize(pageUser.getSize());
        userResponse.setTotalElements(pageUser.getTotalElements());
        userResponse.setTotalPages(pageUser.getTotalPages());
        userResponse.setLastPage(pageUser.isLast());
        return userResponse;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",Integer.toString(userId)));
        this.userRepo.delete(user);
    }

    private User dtoToUser(UserDto userDto){
        User user = this.modelMapper.map(userDto,User.class);
        return user;
    }

    private UserDto userToDto(User user){
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }
}
