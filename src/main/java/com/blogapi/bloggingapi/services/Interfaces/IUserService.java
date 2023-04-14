package com.blogapi.bloggingapi.services.Interfaces;

import java.util.List;

import com.blogapi.bloggingapi.payload.UserDTO;
import com.blogapi.bloggingapi.payload.UserResponse;

public interface IUserService {
    UserDTO createUser(UserDTO user);

    UserDTO updateUser(UserDTO user, Integer userId);

    UserDTO getUserById(Integer userId);

    List<UserDTO> getAllUsers();

    UserResponse getUserByPages(Integer pageNumber, Integer pageSize);

    void deleteUser(Integer userId);
}
