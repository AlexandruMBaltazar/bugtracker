package com.alex.exceptionhandler.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.Map;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public record GlobalApiException(
        String message,
        HttpStatus httpStatus,
        ZonedDateTime timestamp,
        Map<String, String> validationErrors
) {
}
