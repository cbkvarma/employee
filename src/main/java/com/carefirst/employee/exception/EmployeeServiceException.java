package com.carefirst.employee.exception;

import org.springframework.http.HttpStatus;

public class EmployeeServiceException extends RuntimeException {

	private static final long serialVersionUID = -7855913415080445688L;
	
	private final ErrorCategory category;
	private final ErrorCode code;
	private final HttpStatus status;
	
	public EmployeeServiceException(ErrorCategory category, ErrorCode code, HttpStatus status, String message) {
		super(message);
		this.category = category;
		this.code = code;
		this.status = status;
	}

	public EmployeeServiceException(ErrorCategory category, ErrorCode code, HttpStatus status, String message, Throwable cause) {
		super(message, cause);
		this.category = category;
		this.code = code;
		this.status = status;
	}

	public ErrorCategory getCategory() {
		return this.category;
	}

	public ErrorCode getCode() {
		return this.code;
	}

	public HttpStatus getStatus() {
		return status;
	}
}
