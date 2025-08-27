package com.example.book_library.service;

import com.example.book_library.dto.UserDTO;
import com.example.book_library.entity.User;
import com.example.book_library.exception.UnavailableEmailException;
import com.example.book_library.repository.UserRepository;
import jakarta.security.auth.message.AuthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp(){
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userServiceImpl = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    void shouldRegisterUserSuccessfully() throws AuthException {
        when(userRepository.findByEmail("newuser@domain.com")).thenReturn(any());
        when(passwordEncoder.encode("rawpass")).thenReturn("hashedpass");

        User savedUser = new User("newuser@domain.com", "John Smith", "hashedpass");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userServiceImpl.registerUser(new UserDTO(
                "newuser@domain.com",
                "John Smith",
                "hashedpass"
        ));

        assertThat(result.getEmail()).isEqualTo("newuser@domain.com");
        assertThat(result.getPassword()).isEqualTo("hashedpass");
    }

    @Test
    void shouldThrowIfEmailExists(){
        when(userRepository.findByEmail("newuser@domain.com")).thenReturn(Optional.of(
                new User("newuser@domain.com", "Bob", "hashedpassword")));

        assertThatThrownBy(() -> userServiceImpl
                .registerUser(new UserDTO("newuser@domain.com","John","hashedpassword")))
                .isInstanceOf(UnavailableEmailException.class)
                .hasMessage("This email is already registered.");

        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldFindUserByEmail(){
        User user = new User("john@domain.com", "John", "pass123");
        when(userRepository.findByEmail("john@domain.com")).thenReturn(Optional.of(user));

        UserDTO dto = new UserDTO("john@domain.com", "John", "pass123");
        Optional<User> found = userServiceImpl.findByEmail(dto);
        assertThat(found).isPresent().get().isEqualTo(user);
    }
}
