package org.nphc.controllers;

import org.nphc.services.EmployeeService;
import org.nphc.utilities.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeDeleteController {
	
	@Autowired
	EmployeeService fileService;
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<ResponseMessage> deleteEmployee(@PathVariable("id") String id) {
		fileService.deleteEmployee(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Successfully deleted"));
		
	}
}
