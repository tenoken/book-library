package com.example.book_library.exception;

public class ExpiredJwtException extends RuntimeException {
    public ExpiredJwtException() { super("JWT is expired or invalid.");}
}