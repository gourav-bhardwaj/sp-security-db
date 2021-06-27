package com.sp.exception;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7897258757670702780L;
	private HttpStatus status;

	public ApplicationException(HttpStatus status, String message, Object... args) {
		super(String.format(message, args));
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

}
