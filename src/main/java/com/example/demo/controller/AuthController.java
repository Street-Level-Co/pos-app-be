package com.example.demo.controller;

import com.example.demo.service.AuthService;
import com.example.demo.transfer.auth.LoginRequest;
import com.example.demo.transfer.auth.RefreshRequest;
import com.example.demo.transfer.auth.RegisterRequest;
import com.example.demo.util.StandardResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<StandardResponse> register(@RequestBody @Valid RegisterRequest input) {
        authService.register(input);
        return new ResponseEntity<>(
                new StandardResponse("Success", "User registered"),
                HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<StandardResponse> login(@RequestBody @Valid LoginRequest input) {
        return new ResponseEntity<>(
                new StandardResponse("Success", authService.login(input)),
                HttpStatus.OK);
    }

    @PostMapping("refresh")
    public ResponseEntity<StandardResponse> refresh(@RequestBody @Valid RefreshRequest input) {
        return new ResponseEntity<>(
                new StandardResponse("Success", authService.refresh(input.getRefreshToken())),
                HttpStatus.OK);
    }

    @PostMapping("logout")
    public ResponseEntity<StandardResponse> logout(@RequestBody @Valid RefreshRequest input) {
        authService.logout(input.getRefreshToken());
        return new ResponseEntity<>(
                new StandardResponse("Success", "Logged out"),
                HttpStatus.OK);
    }
}
