package com.example.demo.rest.auth;

import com.example.demo.ITBase;
import com.example.demo.repository.UserRepository;
import com.example.demo.transfer.auth.AuthResponse;
import com.example.demo.transfer.auth.LoginRequest;
import com.example.demo.transfer.auth.RefreshRequest;
import com.example.demo.transfer.auth.RegisterRequest;
import com.example.demo.util.StandardResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class AuthIT extends ITBase {

    @Autowired
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String username = RandomStringUtils.random(10, true, true) + "@email.com";
    private final String password = RandomStringUtils.random(12, true, true) + "1A";

    @Override
    @BeforeEach
    public void setup() {
        super.setup();
    }

    @Override
    @AfterEach
    public void cleanup() {
        super.cleanup();
        userRepository.deleteAll();
    }

    @Test
    void testRegisterAndLogin() throws Exception {
        registerUser();

        MockHttpServletResponse response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoginRequest(username, password))))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        AuthResponse authResponse = extractAuthResponse(response);
        Assertions.assertNotNull(authResponse.getAccessToken());
        Assertions.assertNotNull(authResponse.getRefreshToken());
        Assertions.assertEquals("Bearer", authResponse.getTokenType());
    }

    @Test
    void testLogin_wrongPassword() throws Exception {
        registerUser();

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoginRequest(username, "wrong-password"))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testRefresh_issuesNewTokensAndRotatesOldOne() throws Exception {
        registerUser();
        AuthResponse loginResponse = login();

        MockHttpServletResponse response = mockMvc.perform(post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RefreshRequest(loginResponse.getRefreshToken()))))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        AuthResponse refreshed = extractAuthResponse(response);
        Assertions.assertNotNull(refreshed.getAccessToken());
        Assertions.assertNotEquals(loginResponse.getRefreshToken(), refreshed.getRefreshToken());

        // the old refresh token must no longer be usable
        mockMvc.perform(post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RefreshRequest(loginResponse.getRefreshToken()))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testLogout_revokesRefreshToken() throws Exception {
        registerUser();
        AuthResponse loginResponse = login();

        mockMvc.perform(post("/api/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RefreshRequest(loginResponse.getRefreshToken()))))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RefreshRequest(loginResponse.getRefreshToken()))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testProtectedEndpoint_requiresAccessToken() throws Exception {
        mockMvc.perform(get("/api/client/{clientID}", java.util.UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    private void registerUser() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RegisterRequest(username, password))))
                .andExpect(status().isCreated());
    }

    private AuthResponse login() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoginRequest(username, password))))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        return extractAuthResponse(response);
    }

    private AuthResponse extractAuthResponse(MockHttpServletResponse response) throws Exception {
        StandardResponse standardResponse = objectMapper.readValue(response.getContentAsString(), StandardResponse.class);
        return objectMapper.convertValue(standardResponse.getData(), AuthResponse.class);
    }
}
