package com.example.book_library.controller;

import com.example.book_library.dto.AuthRequestDTO;
import com.example.book_library.dto.UserDTO;
import com.example.book_library.entity.User;
import com.example.book_library.infra.security.JwtFilter;
import com.example.book_library.infra.security.JwtTokenProvider;
import com.example.book_library.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtFilter jwtFilter;

    @MockitoBean
    private UserServiceImpl userServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void souldCreateUser_whenIpuntIsValid() throws Exception {
        User user = new User("alice@example.com", "Alice", "P@assw0rd!");
        UserDTO userDTO = new UserDTO("alice@example.com", "Alice", "P@assw0rd!");

        when(userServiceImpl.registerUser(any())).thenReturn(user);

        mockMvc.perform(post("/api/users/register")
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("alice@example.com"));
    }

    @Test
    void shoudlReturn400_whenEmailIsInvalid() throws Exception {
        UserDTO userDTO = new UserDTO("bad-email", "Alice", "P@assw0rd!");

        mockMvc.perform(post("/api/users/register")
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest());
    }
}
