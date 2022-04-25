package com.example.bank_app.error;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserEntityNotFoundException extends RuntimeException {
	 public UserEntityNotFoundException(String name, Object value) {
	        super("Entity is not found, "+ name +": " + value);
	    }
}
