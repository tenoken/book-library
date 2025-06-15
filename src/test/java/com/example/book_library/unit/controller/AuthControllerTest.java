package com.example.book_library.unit.controller;

import com.example.book_library.controller.AuthController;
import com.example.book_library.controller.AuthRequest;
import com.example.book_library.infra.security.JwtTokenProvider;
import com.example.book_library.service.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private AuthenticationManager authenticationManager;
    @MockitoBean
    private JwtTokenProvider jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void shouldReturnTokenWhenCredentialsAreValid() throws Exception {
        var request = new AuthRequest("teste123@teste.com","password");
        var authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.createToken(any())).thenReturn("mock-token");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value("mock-token"));
    }

    @Test
    public void shouldReturn401WhenCredentialsAreInvalid() throws Exception {
        var request = new AuthRequest("user@example.com","wrongpassword");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad Credentials"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.message")
                .value("Bad Credentials"));
    }

    @Test
    public void shouldReturn401WhenCredentialsAreNull() throws Exception {
        var request = new AuthRequest(null,null);

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad Credentials"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail")
                        .value("Invalid request content."));
    }

    @Test
    public void shouldReturn401WhenCredentialsAreEmpty() throws Exception {
        var request = new AuthRequest("","");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad Credentials"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail")
                        .value("Invalid request content."));
    }

    @Test
    public void shouldReturn400WhenEmailIsInvalid() throws Exception {
        var request = new AuthRequest("notvalidemail","");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad Credentials"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail")
                        .value("Invalid request content."));
    }

    @Test
    public void shouldReturn400WhenEmailIsValidAndPasswordEmpty() throws Exception {
        var request = new AuthRequest("user@example.com","");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad Credentials"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail")
                        .value("Invalid request content."));
    }
}
