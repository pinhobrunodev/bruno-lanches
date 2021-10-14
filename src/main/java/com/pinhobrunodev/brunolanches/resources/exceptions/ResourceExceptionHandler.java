package com.pinhobrunodev.brunolanches.resources.exceptions;

import com.pinhobrunodev.brunolanches.services.exceptions.DatabaseException;
import com.pinhobrunodev.brunolanches.services.exceptions.ResourceNotFoundException;
import com.pinhobrunodev.brunolanches.services.exceptions.UnprocessableActionException;
import com.pinhobrunodev.brunolanches.services.exceptions.ValidatingNumberOfCategoriesException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceHandler(ResourceNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Resource not found");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}

	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> databaseHandler(DatabaseException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Database Exception");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}

	@ExceptionHandler(UnprocessableActionException.class)
	public ResponseEntity<StandardError> unprocessableAction(UnprocessableActionException e,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Unprocessable Action");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}

	@ExceptionHandler(ValidatingNumberOfCategoriesException.class)
	public ResponseEntity<StandardError> validatingNumberOfCategories(ValidatingNumberOfCategoriesException e,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Unprocessable Action");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidationError error = new ValidationError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Validation Exception");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());

		for (FieldError f : e.getBindingResult().getFieldErrors()) {
			error.add(f.getField(), f.getDefaultMessage());
		}

		return ResponseEntity.status(status).body(error);
	}
}
