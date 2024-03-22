package com.carefirst.employee.exception;

import org.springframework.dao.DataAccessException;

public class DatabaseOperationException extends Exception {

	private static final long serialVersionUID = -1351461638773583533L;
	
	public DatabaseOperationException(String message) {
		super(message);
	}

	public DatabaseOperationException(String message, DataAccessException e) {
		super(message, e);
	}
}
