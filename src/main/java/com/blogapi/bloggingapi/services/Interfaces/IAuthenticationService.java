package com.blogapi.bloggingapi.services.Interfaces;

import com.blogapi.bloggingapi.payload.AuthenticationRequest;
import com.blogapi.bloggingapi.payload.AuthenticationResponse;
import com.blogapi.bloggingapi.payload.RegisterRequest;

public interface IAuthenticationService {
    public AuthenticationResponse register(RegisterRequest request);

    public AuthenticationResponse authenticate(AuthenticationRequest request);
}
