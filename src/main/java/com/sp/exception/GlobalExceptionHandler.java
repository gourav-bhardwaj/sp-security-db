package com.sp.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.sp.dto.ExceptionResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({
		ApplicationException.class
	})
	public ResponseEntity<ExceptionResponse> handleApplicationException(ApplicationException ex, WebRequest request) {
		ExceptionResponse response = new ExceptionResponse(ex.getStatus().value(), ex.getMessage(), ((ServletWebRequest)request).getRequest().getRequestURI(), LocalDateTime.now());
		return new ResponseEntity<>(response, ex.getStatus());
	}
	
	@ExceptionHandler({
		IllegalArgumentException.class
	})
	public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
		ExceptionResponse response = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ((ServletWebRequest)request).getRequest().getRequestURI(), LocalDateTime.now());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
}
