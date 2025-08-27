package com.example.book_library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @NonNull
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 50, message = "Field maximum size is 50 characters")
    private String email;

    @NonNull
    @Column(nullable = false)
    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Field maximum size is 50 characters")
    @Size(min = 2, message = "Field should have at least 2 characters length")
    private String name;

    @NonNull
    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    @Size(max = 255, message = "Field maximum size is 255 characters.")
    private String password;
}
