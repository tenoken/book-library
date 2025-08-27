package com.example.book_library.controller;

import com.example.book_library.dto.UserDTO;
import com.example.book_library.entity.User;
import com.example.book_library.service.UserServiceImpl;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl)
    {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID id) {
        UserDTO user = userServiceImpl.getById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) throws AuthException {
        User savedUser = userServiceImpl.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id,
                                              @RequestBody @Valid UserDTO updateDto) {
        UserDTO updatedUser = userServiceImpl.update(id, updateDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userServiceImpl.delete(id);
        return ResponseEntity.noContent().build();
    }
}
