package com.alex.exceptionhandler.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GlobalApiRequestException extends RuntimeException {
    private HttpStatus httpStatus;

    public GlobalApiRequestException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public GlobalApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
