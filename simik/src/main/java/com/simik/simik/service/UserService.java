package com.simik.simik.service;

import com.simik.simik.dto.LoginRequest;
import com.simik.simik.dto.LoginResponse;
import com.simik.simik.dto.RegisterRequest;

public interface UserService {

    void registerUser(RegisterRequest request);

    LoginResponse loginUser(LoginRequest request);
}