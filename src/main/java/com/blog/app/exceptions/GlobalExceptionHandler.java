package com.blog.app.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.app.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {

		String message = exception.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);

		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(
			MethodArgumentNotValidException exception) {

		Map<String, String> response = new HashMap<>();

		exception.getBindingResult().getAllErrors().forEach(error -> {
			String message = error.getDefaultMessage();
			String fieldValue = ((FieldError) (error)).getField();
			response.put(fieldValue, message);
		});

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}

}
