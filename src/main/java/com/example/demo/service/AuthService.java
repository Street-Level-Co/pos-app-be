package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.transfer.auth.AuthResponse;
import com.example.demo.transfer.auth.LoginRequest;
import com.example.demo.transfer.auth.RegisterRequest;

public interface AuthService {
    User register(RegisterRequest input);

    AuthResponse login(LoginRequest input);

    AuthResponse refresh(String refreshToken);

    void logout(String refreshToken);
}
