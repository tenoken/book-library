package com.example.book_library.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validAuthRequest_shouldPassValidation(){
        AuthRequestDTO dto = new AuthRequestDTO("user@example.com", "secret123");
        Set<ConstraintViolation<AuthRequestDTO>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    void blankEmail_shouldFailValidation(){
        AuthRequestDTO dto = new AuthRequestDTO("", "secret123");
        Set<ConstraintViolation<AuthRequestDTO>> violations = validator.validate(dto);
        assertThat(violations).extracting("message").contains("Email is required");
    }

    @Test
    void invalidEmail_shouldFailValidation(){
        AuthRequestDTO dto = new AuthRequestDTO("invalid-email", "secret123");
        Set<ConstraintViolation<AuthRequestDTO>> violations = validator.validate(dto);
        assertThat(violations).extracting("message").contains("Invalid email format");
    }

    @Test
    void blankPassword_shouldFailValidation(){
        AuthRequestDTO dto = new AuthRequestDTO("user@example.com", "");
        Set<ConstraintViolation<AuthRequestDTO>> violations = validator.validate(dto);
        assertThat(violations).extracting("message").contains("Password is required");
    }
}
