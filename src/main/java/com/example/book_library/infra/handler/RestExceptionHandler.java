package com.example.book_library.infra.handler;

import com.example.book_library.exception.ExpiredJwtException;
import com.example.book_library.exception.UserNotAuthenticatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler  {

    @ExceptionHandler(UserNotAuthenticatedException.class)
    private ResponseEntity<RestErrorMessage> userNotAuthenticatedHandler(UserNotAuthenticatedException exception) {
        RestErrorMessage message = new RestErrorMessage(HttpStatus.UNAUTHORIZED, exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    private ResponseEntity<RestErrorMessage> expiredJwtHandler(ExpiredJwtException exception) {
        RestErrorMessage message = new RestErrorMessage(HttpStatus.UNAUTHORIZED, exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }
}
