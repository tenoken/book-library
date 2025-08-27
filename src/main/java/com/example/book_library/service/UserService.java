package com.example.book_library.service;

import com.example.book_library.dto.UserDTO;

import java.util.UUID;

public interface UserService {
    UserDTO getById(UUID id);
    UserDTO update(UUID id, UserDTO dto);
    void delete(UUID id);
}
