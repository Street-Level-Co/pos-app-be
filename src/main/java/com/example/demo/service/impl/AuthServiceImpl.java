package com.example.demo.service.impl;

import com.example.demo.exception.EntryAlreadyExistsException;
import com.example.demo.exception.LoginFailedException;
import com.example.demo.exception.TokenRefreshException;
import com.example.demo.model.RefreshToken;
import com.example.demo.model.User;
import com.example.demo.repository.UserOrganizationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;
import com.example.demo.service.AuthService;
import com.example.demo.service.RefreshTokenService;
import com.example.demo.transfer.auth.AuthResponse;
import com.example.demo.transfer.auth.LoginRequest;
import com.example.demo.transfer.auth.OrganizationSummary;
import com.example.demo.transfer.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String DEFAULT_ROLE = "USER";

    private final UserRepository userRepository;
    private final UserOrganizationRepository userOrganizationRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public User register(RegisterRequest input) {
        if (userRepository.existsByUsername(input.getUsername())) {
            throw new EntryAlreadyExistsException("Username already registered.");
        }
        User user = new User(input.getUsername(), passwordEncoder.encode(input.getPassword()), DEFAULT_ROLE);
        return userRepository.save(user);
    }

    @Override
    public AuthResponse login(LoginRequest input) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword()));
        } catch (BadCredentialsException | DisabledException e) {
            throw new LoginFailedException("Invalid username or password");
        }

        User user = userRepository.findByUsername(input.getUsername())
                .orElseThrow(() -> new LoginFailedException("Invalid username or password"));

        return issueTokens(user);
    }

    @Override
    public AuthResponse refresh(String rawRefreshToken) {
        RefreshToken refreshToken = refreshTokenService.validate(rawRefreshToken);
        User user = userRepository.findById(refreshToken.getUserId())
                .orElseThrow(() -> new TokenRefreshException("Refresh token is invalid"));

        refreshTokenService.revoke(rawRefreshToken);
        return issueTokens(user);
    }

    @Override
    public void logout(String rawRefreshToken) {
        refreshTokenService.revoke(rawRefreshToken);
    }

    private AuthResponse issueTokens(User user) {
        String accessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = refreshTokenService.issue(user.getId());
        List<OrganizationSummary> organizations = userOrganizationRepository.findOrganizationsByUserId(user.getId())
                .stream()
                .map(org -> new OrganizationSummary(org.getId(), org.getOrgName()))
                .toList();
        return new AuthResponse(accessToken, newRefreshToken, "Bearer", jwtService.getAccessTokenExpirationMs(), user.getId(), organizations);
    }
}
