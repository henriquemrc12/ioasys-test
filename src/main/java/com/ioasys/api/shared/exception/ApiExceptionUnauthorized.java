package com.ioasys.api.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ApiExceptionUnauthorized extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ApiExceptionUnauthorized(String message) {
        super(message);
    }

}