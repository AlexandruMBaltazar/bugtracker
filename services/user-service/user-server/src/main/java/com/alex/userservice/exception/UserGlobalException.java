package com.alex.userservice.exception;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.Map;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public record UserGlobalException(
        String message,
        HttpStatus httpStatus,
        ZonedDateTime timestamp,
        Map<String, String> validationErrors
) {
}
