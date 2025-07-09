package com.example.book_library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Minimum eight characters, at least one uppercase letter," +
                    " one lowercase letter, one number and one special character"
    )
    private String password;

    public UserDTO (){

    }

    public UserDTO (String email, String name, String password){
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getPassword() { return password; }
}
