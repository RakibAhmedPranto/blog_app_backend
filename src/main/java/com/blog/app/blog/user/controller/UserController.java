package com.blog.app.blog.user.controller;

import com.blog.app.blog.ExcelFileUpload.ExcelHelper;
import com.blog.app.blog.user.payload.UserDto;
import com.blog.app.blog.user.service.UserService;
import com.blog.app.blog.utils.ApiResponse;
import com.blog.app.blog.utils.PostResponse;
import com.blog.app.blog.utils.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createdUser = this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable Integer userId){
        UserDto updatedUser = this.userService.updateUser(userDto,userId);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/uploadExcel")
    public ResponseEntity<?> uploadUserByExcel(@RequestParam("file")MultipartFile file){
        if(ExcelHelper.checkExcelFormat(file)){
            this.userService.createUserByExcel(file);
            return ResponseEntity.ok(Map.of("message","File is uploaded and data is saved"));
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Upload Excel File");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
        this.userService.deleteUser(userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully",true), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<UserResponse> getAllUser(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        UserResponse userResponse = this.userService.getAllUsers(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<UserResponse>(userResponse,HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }
}
