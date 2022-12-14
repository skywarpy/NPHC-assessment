package org.nphc.exceptions;

@SuppressWarnings("serial")
public class DateFormatException extends Exception {

	public DateFormatException() {
		super();
	}
	
	public DateFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public DateFormatException(String message) {
		super(message);
	}

	public DateFormatException(Throwable cause) {
		super(cause);
	}
	
}
