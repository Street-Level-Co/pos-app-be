package com.example.demo.service;

import com.example.demo.model.RefreshToken;

import java.util.UUID;

public interface RefreshTokenService {
    String issue(UUID userId);

    RefreshToken validate(String rawToken);

    void revoke(String rawToken);

    void revokeAllForUser(UUID userId);
}
