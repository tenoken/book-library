package com.example.book_library.controller;

import com.example.book_library.exception.UserNotAuthenticatedException;
import com.example.book_library.infra.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JwtTokenProvider tokenProvider;

    public AuthController (AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody @Valid AuthRequest request){
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            if (auth == null || !auth.isAuthenticated())
                throw new UserNotAuthenticatedException();

        } catch (AuthenticationException e){
            return ResponseEntity.status(401).body(Map.of("message", e.getMessage()));
        }

        String token = tokenProvider.createToken(request.getEmail());

        return ResponseEntity.ok(Map.of("token", token));
    }
}
