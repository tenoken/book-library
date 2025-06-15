package com.example.book_library.controller;

import com.example.book_library.dto.UserDTO;
import com.example.book_library.entity.User;
import com.example.book_library.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO){

        //TODO: VERIFY WHETHER THE USER ALREADY EXIST
        User savedUser = userService.registerUser(userDTO);
        return ResponseEntity.status(201).body(savedUser);
        //return userDTO;
    }
}
