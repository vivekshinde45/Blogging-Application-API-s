package com.blogapi.bloggingapi.services.Implementation;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogapi.bloggingapi.config.JwtService;
import com.blogapi.bloggingapi.entities.Role;
import com.blogapi.bloggingapi.entities.User;
import com.blogapi.bloggingapi.exceptions.ResourceNotFoundException;
import com.blogapi.bloggingapi.payload.AuthenticationRequest;
import com.blogapi.bloggingapi.payload.AuthenticationResponse;
import com.blogapi.bloggingapi.payload.RegisterRequest;
import com.blogapi.bloggingapi.repositories.RoleRepository;
import com.blogapi.bloggingapi.repositories.UserRepository;
import com.blogapi.bloggingapi.services.Interfaces.IAuthenticationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
        private final UserRepository _userRepository;

        private final RoleRepository _roleRepository;

        private final PasswordEncoder _passwordEncoder;

        private final JwtService _jwtService;

        private final AuthenticationManager _authenticationManager;

        public AuthenticationResponse register(RegisterRequest request) {
                Role role = this._roleRepository.findById(1)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Role ",
                                                "Role id",
                                                1));
                Set<Role> roleSet = new HashSet<>();
                roleSet.add(role);
                User user = new User();
                user.setName(request.getName());
                user.setEmail(request.getEmail());
                user.setPassword(this._passwordEncoder.encode(request.getPassword()));
                user.setRoles(roleSet);
                _userRepository.save(user);
                var jwtToken = _jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build();
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                _authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getUsername(),
                                                request.getPassword()));
                var user = this._userRepository.findByEmail(request.getUsername())
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                var jwtToken = _jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build();
        }

}
