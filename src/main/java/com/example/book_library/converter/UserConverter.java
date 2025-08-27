package com.example.book_library.converter;

import com.example.book_library.dto.UserDTO;
import com.example.book_library.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDTO toDto(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        user.setId(user.getId());
        user.setEmail(user.getEmail());
        user.setName(user.getName());
        return userDTO;
    }

    public User fromDto(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        return user;
    }
}

