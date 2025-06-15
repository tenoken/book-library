package com.example.book_library.exception;

public class UserNotAuthenticatedException extends RuntimeException{
    public UserNotAuthenticatedException() { super("User could not be authenticated. Email or password might be wrong.");}
}
