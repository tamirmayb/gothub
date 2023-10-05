package com.gothub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomDateTimeParseException extends RuntimeException {

    public CustomDateTimeParseException() {super(); }

    public CustomDateTimeParseException(String message) {
        super(message);
    }
}