package com.alex.userservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserGlobalRequestException extends RuntimeException{
    private HttpStatus httpStatus;

    public UserGlobalRequestException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public UserGlobalRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
