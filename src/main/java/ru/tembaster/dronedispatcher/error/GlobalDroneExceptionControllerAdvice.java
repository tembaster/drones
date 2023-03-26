package ru.tembaster.dronedispatcher.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalDroneExceptionControllerAdvice extends ResponseEntityExceptionHandler {

	public static final String ATTRIBUTE_ERRORS_FIELD_NAME = "errors";

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
																  HttpHeaders headers,
																  HttpStatus status,
																  WebRequest request) {
		List<String> errors = ex.getBindingResult()
								.getFieldErrors()
								.stream()
								.map(DefaultMessageSourceResolvable::getDefaultMessage)
								.collect(Collectors.toList());
		return new ResponseEntity<>(Map.of(ATTRIBUTE_ERRORS_FIELD_NAME, errors), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> constraintViolationException(ConstraintViolationException ex) {
		List<String> errors = ex.getConstraintViolations()
								.stream()
								.map(ConstraintViolation::getMessage)
								.collect(Collectors.toList());
		return new ResponseEntity<>(Map.of(ATTRIBUTE_ERRORS_FIELD_NAME, errors), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> resourceNotFoundException(ResourceNotFoundException ex) {
		return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DroneLoadException.class)
	public ResponseEntity<Object> droneLoadException(DroneLoadException ex) {
		return new ResponseEntity<>(Map.of(ATTRIBUTE_ERRORS_FIELD_NAME, ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

}
