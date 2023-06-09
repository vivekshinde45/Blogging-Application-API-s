package com.blogapi.bloggingapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blogapi.bloggingapi.payload.ApiResponseBody;
import com.blogapi.bloggingapi.payload.UserDTO;
import com.blogapi.bloggingapi.payload.UserResponse;
import com.blogapi.bloggingapi.services.Interfaces.IUserService;

import jakarta.validation.Valid;

@RestController()
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private IUserService _userService;

    // POST => Create user
    @PostMapping("/")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = this._userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // PUT => Update existing user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO userDTO, @PathVariable("userId") Integer userId) {
        UserDTO updatedUser = this._userService.updateUser(userDTO, userId);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // DELETE => delete existing user
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseBody> delete(@PathVariable("userId") Integer userId) {
        this._userService.deleteUser(userId);
        ApiResponseBody responseBody = new ApiResponseBody("User deleted successfully", true);
        return ResponseEntity.ok(responseBody);
    }

    // GET => Get all users
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> users = this._userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // GET => Get users by page
    @GetMapping("/page")
    public ResponseEntity<UserResponse> getByPage(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize) {
        UserResponse users = this._userService.getUserByPages(pageNumber, pageSize);
        return ResponseEntity.ok(users);
    }

    // GET/{id} => Get user by Id
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getById(@PathVariable("userId") Integer userId) {
        UserDTO user = this._userService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
