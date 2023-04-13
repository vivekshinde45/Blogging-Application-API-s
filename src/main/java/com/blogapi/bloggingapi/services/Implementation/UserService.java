package com.blogapi.bloggingapi.services.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapi.bloggingapi.entities.User;
import com.blogapi.bloggingapi.exceptions.ResourceNotFoundException;
import com.blogapi.bloggingapi.payload.UserDTO;
import com.blogapi.bloggingapi.repositories.UserRepository;
import com.blogapi.bloggingapi.services.Interfaces.IUserService;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository _userRepository;

    @Override
    public UserDTO createUser(UserDTO userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this._userRepository.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDto, Integer userId) {
        try {
            User user = this._userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "User",
                            " Id ",
                            userId));
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setAbout(userDto.getAbout());

            // save into DB
            User savedUser = this._userRepository.save(user);

            return this.userToDto(savedUser);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    @Override
    public UserDTO getUserById(Integer userId) {
        try {
            User user = this._userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "User",
                            " Id ",
                            userId));
            return this.userToDto(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = this._userRepository.findAll();

        // mapped to userDTO
        List<UserDTO> userDtos = users.stream().map(
                user -> this.userToDto(user)).collect(Collectors.toList());

        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        try {
            User user = this._userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "User",
                            " Id ",
                            userId));
            this._userRepository.delete(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public User dtoToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setAbout(userDTO.getAbout());
        return user;
    }

    public UserDTO userToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setAbout(user.getAbout());
        return userDTO;
    }

}
