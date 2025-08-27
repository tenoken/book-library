package com.example.book_library.entity;

import com.example.book_library.dto.AuthRequestDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserTest {

    private Validator validator;

    @BeforeEach
    void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validEntity_shouldPassValidation(){
        User entity = new User("user@example.com","John Smith","secret123");
        Set<ConstraintViolation<User>> violations = validator.validate(entity);
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    void validEntityWithMininumLengthName_shouldPassValidation(){
        User entity = new User("user@example.com","Yu","secret123");
        Set<ConstraintViolation<User>> violations = validator.validate(entity);
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    void validEntityWithMaximumLengthName_shouldPassValidation(){
        String longName = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
        User entity = new User("user@example.com",longName,"secret123");
        Set<ConstraintViolation<User>> violations = validator.validate(entity);
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    void blankEmail_shouldFailValidation(){
        User entity = new User("","John Smith", "secret123");
        Set<ConstraintViolation<User>> violations = validator.validate(entity);
        Assertions.assertThat(violations).extracting("message").contains("Email is required");
    }

    @Test
    void invalidEmail_shouldFailValidation(){
        User entity = new User("invalid-email","John Smith", "secret123");
        Set<ConstraintViolation<User>> violations = validator.validate(entity);
        Assertions.assertThat(violations).extracting("message").contains("Invalid email format");
    }

    @Test
    void blankPassword_shouldFailValidation(){
        User entity = new User("user@example.com","John Smith", "");
        Set<ConstraintViolation<User>> violations = validator.validate(entity);
        Assertions.assertThat(violations).extracting("message").contains("Password is required");
    }

    @Test
    void blankName_shouldFailValidation(){
        User entity = new User("user@example.com","","secret123");
        Set<ConstraintViolation<User>> violations = validator.validate(entity);

        Assertions.assertThat(violations)
                .extracting("message")
                .contains("Field should have at least 2 characters length");
    }

//    @Test
//    void nullName_shouldFailValidation(){
//        User entity = new User("user@example.com",null,"secret123");
//        Set<ConstraintViolation<User>> violations = validator.validate(entity);
//
//        Assertions.assertThat(violations)
//                .extracting("message")
//                .contains("Name is required");
//    }

    @Test
    void longName_shouldFailValidation(){
        String longName = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
        User entity = new User("user@example.com",longName,"secret123");
        Set<ConstraintViolation<User>> violations = validator.validate(entity);

        Assertions.assertThat(violations)
                .extracting("message")
                .contains("Field maximum size is 50 characters");
        Assertions.assertThat(longName.length() > 50);
    }
}
