package com.carefirst.employee.exception;

import org.springframework.http.HttpStatus;

public class MismatchedParametersException extends EmployeeServiceException {

	private static final long serialVersionUID = 469244802647620055L;

	public MismatchedParametersException() {
		super(ErrorCategory.VALIDATION, ErrorCode.MISMATCHED_PARAMETERS,  HttpStatus.BAD_REQUEST, 
				"Mismatched path and body parameters.");
	}

}
