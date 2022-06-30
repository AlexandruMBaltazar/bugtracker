package com.alex.user.exception;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record UserGlobalException(
        String message,
        Throwable throwable,
        HttpStatus httpStatus,
        ZonedDateTime timestamp
) {
}
