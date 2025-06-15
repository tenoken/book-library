package com.example.book_library.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthRequest {

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    public AuthRequest(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail(){
      return email;
    }

    public String getPassword(){
        return password;
    }
}
