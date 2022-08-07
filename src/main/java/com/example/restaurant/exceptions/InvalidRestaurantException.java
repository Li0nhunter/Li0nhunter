package com.example.restaurant.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Invalid Review")
public class InvalidRestaurantException extends Exception{
    public InvalidRestaurantException(String e) {
        super(e);
    }
}
