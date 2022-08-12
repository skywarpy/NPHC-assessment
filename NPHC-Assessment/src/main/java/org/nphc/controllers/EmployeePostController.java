package org.nphc.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.json.JSONObject;
import org.nphc.entities.Employee;
import org.nphc.exceptions.DateFormatException;
import org.nphc.exceptions.DuplicateIDFoundException;
import org.nphc.exceptions.SalaryFormatException;
import org.nphc.services.EmployeeService;
import org.nphc.utilities.CSVReader;
import org.nphc.utilities.DateFormatterUtil;
import org.nphc.utilities.ResponseMessage;
import org.nphc.utilities.SalaryParserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * RestController dealing with all POST requests specified.
 */

@RestController
public class EmployeePostController {
	
	@Autowired
	EmployeeService fileService;
	
	private DateFormatterUtil dateFormatterUtil = new DateFormatterUtil();
	
	private SalaryParserUtil salaryParserUtil = new SalaryParserUtil();
	
	@PostMapping("/users/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@Valid @RequestParam("file") MultipartFile file) {
		String message = "";
		if (CSVReader.hasCSVFormat(file)) {
			try {
				if (fileService.save(file)) {
					message = "Data has been successfully updated using " + file.getOriginalFilename();
			        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(message));
				} else {
				message = "Success but no data updated";
		        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
				}
				
			} catch (DuplicateIDFoundException e) {
				message = e.getMessage();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
			} catch (SalaryFormatException e) {
		        message = e.getMessage();
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
			} catch (DateFormatException e) {
				message = e.getMessage();
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
			} catch (TransactionSystemException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Invalid salary"));
			}
		} message = "File has to be in CSV format";
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
		
	}
	
	@PostMapping("/users")
	public ResponseEntity<ResponseMessage> saveEmployee(@Valid @RequestBody String payload) {
		String message = "";
		JSONObject obj = new JSONObject(payload);
		String employeeId = obj.getString("id");
		String employeeName = obj.getString("name");
		String employeeLogin = obj.getString("login");
		String employeeDate = obj.getString("startDate");
		List<Employee> employees = fileService.getAllEmployees();
		if (fileService.employeeExists(employeeId)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Employee ID already exists"));
		} 
		for (Employee emp : employees) {
			if (emp.getLogin().equals(employeeLogin)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Employee login not unique"));
			}
		}
		
		try {
			LocalDate employeeDateFormatted = dateFormatterUtil.getFormattedDate(employeeDate);
			BigDecimal employeeSalaryParsed = salaryParserUtil.salaryJSON(obj);
			Employee employee = new Employee(employeeId, employeeName, employeeLogin, employeeSalaryParsed, employeeDateFormatted);
			fileService.addEmployee(employee);
			return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("Successfully created"));
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

	
}
