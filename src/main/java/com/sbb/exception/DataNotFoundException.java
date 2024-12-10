package com.sbb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DataNotFoundException() {
	}

	public DataNotFoundException(String message) {
		super(message);
	}

	public DataNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
