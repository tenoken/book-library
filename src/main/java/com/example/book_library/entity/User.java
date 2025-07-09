package com.example.book_library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 50, message = "Field maximum size is 50 characters")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Field maximum size is 50 characters")
    @Size(min = 2, message = "Field should have at least 2 characters length")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    @Size(max = 50, message = "Field maximum size is 50 characters.")
    private String password;

    public User() {

    }

    public User(String email, String name, String password) {
        this.email = email;
        this.name  = name;
        this.password = password;
    }

    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getPassword() { return password; }
}
