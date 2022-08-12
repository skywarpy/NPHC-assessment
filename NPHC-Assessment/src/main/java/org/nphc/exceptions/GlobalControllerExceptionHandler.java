package org.nphc.exceptions;

import org.nphc.utilities.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ResponseMessage> exceptionHandler(EmployeeNotFoundException enfe) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(enfe.getMessage()));
	}
	
	@ExceptionHandler(SalaryFormatException.class)
	public ResponseEntity<ResponseMessage> exceptionHandler(SalaryFormatException sfe) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(sfe.getMessage()));
	}

	
}
