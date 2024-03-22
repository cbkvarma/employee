package com.carefirst.employee.exception;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.buf.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionMapper {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionMapper.class);
	
    @ExceptionHandler(MismatchedParametersException.class)
	protected ResponseEntity<Error> handle(MismatchedParametersException ex, WebRequest request) {
		Error error = new Error(ex.getCategory(), ErrorCode.MISMATCHED_PARAMETERS, ex.getMessage());
		return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
	}
    
    @ExceptionHandler(ResourceNotFoundException.class)
	protected ResponseEntity<Error> handle(ResourceNotFoundException ex, WebRequest request) {
		Error error = new Error(ErrorCategory.RESOURCE, ErrorCode.NOT_FOUND, ex.getMessage());
		return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
	}
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
    	String errorMessage = "Database error occurred";
    	logger.error(ex.getMessage());
    	Error error = new Error(ErrorCategory.DATABASE, ErrorCode.CONSTRAINT_VIOLATION, errorMessage);
        return new ResponseEntity<Error>(error, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity<?> handleDatabaseOperationException(DatabaseOperationException ex, WebRequest request) {
    	logger.error(ex.getMessage());
    	Error error = new Error(ErrorCategory.DATABASE, ErrorCode.INTERNAL, ex.getMessage());
        return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    //@ResponseBody
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorMessages = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errorMessages.add(((FieldError) error).getField() + " - " + error.getDefaultMessage());
        });
        Error error = new Error(ErrorCategory.VALIDATION, ErrorCode.INVALID_REQUEST_DATA, StringUtils.join(errorMessages, ','));
        return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
    }

}
