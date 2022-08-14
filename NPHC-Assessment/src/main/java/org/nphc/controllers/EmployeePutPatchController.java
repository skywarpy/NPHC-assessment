package org.nphc.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.json.JSONObject;
import org.nphc.entities.Employee;
import org.nphc.exceptions.DateFormatException;
import org.nphc.exceptions.SalaryFormatException;
import org.nphc.services.EmployeeService;
import org.nphc.utilities.DateFormatterUtil;
import org.nphc.utilities.ResponseMessage;
import org.nphc.utilities.SalaryParserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeePutPatchController {
	@Autowired
	EmployeeService fileService;
	
	private DateFormatterUtil dateFormatterUtil = new DateFormatterUtil();
	
	private SalaryParserUtil salaryParserUtil = new SalaryParserUtil();
	
	
	@PutMapping("/users/{id}")
	public ResponseEntity<?> putEmployee(@Valid @RequestBody String payload, @PathVariable("id") String id) {
			String message = "";
			JSONObject obj = new JSONObject(payload);
			String employeeName = obj.getString("name");
			String employeeLogin = obj.getString("login");
			String employeeDate = obj.getString("startDate");
			List<String> databaseEmployeeLogins = fileService.getAllEmployeeLogins();
			if(databaseEmployeeLogins.contains(employeeLogin)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Employee login not unique"));
		}
			try {
				LocalDate employeeDateFormatted = dateFormatterUtil.getFormattedDate(employeeDate);
				BigDecimal employeeSalaryParsed = salaryParserUtil.salaryJSON(obj);
				Employee employee = fileService.getEmployee(id);
				employee.setName(employeeName);
				employee.setLogin(employeeLogin);
				employee.setSalary(employeeSalaryParsed);
				employee.setStartDate(employeeDateFormatted);
				fileService.addEmployee(employee);
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Successfully updated"));
			} catch (SalaryFormatException e) {
		        message = e.getMessage();
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
			} catch (DateFormatException e) {
				message = e.getMessage();
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
			} catch (TransactionSystemException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Invalid salary"));
			}
	}
	
	
	@PatchMapping("/users/{id}")
	public ResponseEntity<?> patchEmployee(@Valid @RequestBody Map<String, Object> changes, @PathVariable("id") String id) {
		String message = "";
		Employee employee = fileService.getEmployee(id);
		for (Map.Entry<String, Object> change : changes.entrySet()) {
			switch (change.getKey()) {
				case "name":
					employee.setName((String) change.getValue());
					break;
				case "login":
					List<String> databaseEmployeeLogins = fileService.getAllEmployeeLogins();
					if(databaseEmployeeLogins.contains(change.getValue())) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Employee login not unique"));
					}
					employee.setLogin((String) change.getValue());
					break;
				case "startDate":
					LocalDate employeeDateFormatted;
					try {
						employeeDateFormatted = dateFormatterUtil.getFormattedDate((String) change.getValue());
						employee.setStartDate(employeeDateFormatted);
					} catch (DateFormatException e) {
						message = e.getMessage();
				        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
					}
					
					break;
				case "salary":
					BigDecimal employeeSalaryParsed;
					try {
						employeeSalaryParsed = salaryParserUtil.salary((String) change.getValue().toString());
						employee.setSalary(employeeSalaryParsed);
					} catch (SalaryFormatException e) {
						message = e.getMessage();
				        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
					} 
					break;
			}

		}
		try {
		fileService.addEmployee(employee);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Successfully updated"));
		} catch (TransactionSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Invalid salary"));
		}
		
	}
	
	
}
