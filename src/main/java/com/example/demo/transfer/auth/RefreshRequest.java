package com.example.demo.transfer.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RefreshRequest {
    @NotBlank(message = "Refresh token required")
    private String refreshToken;
}
