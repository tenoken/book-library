package com.example.book_library.service;

import com.example.book_library.dto.UserDTO;
import com.example.book_library.entity.User;
import com.example.book_library.repository.UserRepository;
import jakarta.security.auth.message.AuthException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserDTO dto) throws AuthException {
        String hashedPassword = passwordEncoder.encode(dto.getPassword());

        if( userRepository.findByEmail(dto.getEmail()) != null &&
            userRepository.findByEmail(dto.getEmail()).isPresent()) {
                throw new AuthException("Email already in use.");
        }

        User user = new User(dto.getEmail(), dto.getName(), hashedPassword);
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(UserDTO dto){
        return userRepository.findByEmail(dto.getEmail());
    }
}
