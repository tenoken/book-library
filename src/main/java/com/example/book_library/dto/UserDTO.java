package com.example.book_library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;


import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private UUID id;

    @Email
    @NonNull
    @NotNull
    private String email;

    @NotBlank
    @NonNull
    private String name;

    @NotBlank
    @NonNull
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Minimum eight characters, at least one uppercase letter," +
                    " one lowercase letter, one number and one special character"
    )
    private String password;
}
