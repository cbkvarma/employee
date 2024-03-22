package com.carefirst.employee.exception;

public class Error {

	private final ErrorCategory category;
	private final ErrorCode code;
	private final String description;

	public Error(ErrorCategory category, ErrorCode code, String description) {
		this.category = category;
		this.code = code;
		this.description = description;
	}

	public ErrorCategory getCategory() {
		return this.category;
	}

	public ErrorCode getCode() {
		return this.code;
	}

	public String getDescription() {
		return this.description;
	}

	@Override
	public String toString() {
		return new StringBuilder(this.getClass().getSimpleName()).append("[").append("category:").append(this.category)
				.append(", code:").append(this.code).append(", description:").append(this.description).append("]")
				.toString();
	}

}
