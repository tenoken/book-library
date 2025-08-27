package com.example.book_library.exception;

public class UnavailableEmailException extends RuntimeException {
    public UnavailableEmailException() {
        super("This email is already registered.");
    }
}
