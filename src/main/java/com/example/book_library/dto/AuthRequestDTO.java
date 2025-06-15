package com.example.book_library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AuthRequestDTO {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "",
            message = "Password must have at least 8 characters, one uppercase letter, one digit and one special character"
    )
    private String password;

    public AuthRequestDTO(String email, String password){
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
