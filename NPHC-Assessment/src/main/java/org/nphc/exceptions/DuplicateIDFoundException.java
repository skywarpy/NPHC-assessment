package org.nphc.exceptions;

@SuppressWarnings("serial")
public class DuplicateIDFoundException extends Exception{

	public DuplicateIDFoundException() {
		super();
	}

	public DuplicateIDFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateIDFoundException(String message) {
		super(message);
	}

	public DuplicateIDFoundException(Throwable cause) {
		super(cause);
	}

}
