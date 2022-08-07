package com.example.restaurant.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Invalid User")
public class InvalidUserException extends Exception{
    public InvalidUserException(String e) {
        super(e);
    }
}
