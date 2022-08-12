package org.nphc.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.nphc.entities.Employee;
import org.nphc.entities.EmployeeRequestWrapper;
import org.nphc.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeGetController {
	@Autowired
	EmployeeService fileService;
	
	
	@GetMapping("/users")
	public ResponseEntity<EmployeeRequestWrapper> getAllEmployees(@RequestParam(defaultValue = "0") BigDecimal minSalary, 
			@RequestParam(defaultValue = "4000.00") BigDecimal maxSalary, 
			@RequestParam(defaultValue = "0") int offset,
			@RequestParam(defaultValue = "0") int limit
			) {
		List<Employee> employees = new ArrayList<Employee>();
		try {
			if (limit == 0) {
				employees = fileService.getEmployeesWithLimit(minSalary,maxSalary,offset,Integer.MAX_VALUE);
			} else {
			employees = fileService.getEmployeesWithLimit(minSalary,maxSalary,offset,limit);
			}
			if (employees.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			EmployeeRequestWrapper empRequest = new EmployeeRequestWrapper(employees);
			return new ResponseEntity<EmployeeRequestWrapper>(empRequest, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	    }
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable("id") String id) {
			Employee employee = fileService.getEmployee(id);
			return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}
	
	
}
