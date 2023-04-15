package com.blogapi.bloggingapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blogapi.bloggingapi.entities.User;
import com.blogapi.bloggingapi.exceptions.ResourceNotFoundException;
import com.blogapi.bloggingapi.repositories.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository _userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this._userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User",
                        "email" + username,
                        0));
        return user;
    }

}
