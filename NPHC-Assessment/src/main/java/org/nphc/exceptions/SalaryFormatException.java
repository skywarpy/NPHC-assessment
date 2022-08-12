package org.nphc.exceptions;

@SuppressWarnings("serial")
public class SalaryFormatException extends Exception {

	public SalaryFormatException() {
		super();
	}

	public SalaryFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public SalaryFormatException(String message) {
		super(message);
	}

	public SalaryFormatException(Throwable cause) {
		super(cause);
	}

}
