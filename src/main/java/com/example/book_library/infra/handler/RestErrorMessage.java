package com.example.book_library.infra.handler;
import org.springframework.http.HttpStatus;

public class RestErrorMessage {
    private HttpStatus status;
    private String message;

    public RestErrorMessage(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
