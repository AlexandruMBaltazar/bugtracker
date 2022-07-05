package com.alex.userservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class UserGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UserGlobalRequestException.class})
    public ResponseEntity<Object> handleUserGlobalRequestException(UserGlobalRequestException exception) {
        UserGlobalException userGlobalException = new UserGlobalException(
                exception.getMessage(),
                exception,
                exception.getHttpStatus(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(userGlobalException, exception.getHttpStatus());
    }
}
