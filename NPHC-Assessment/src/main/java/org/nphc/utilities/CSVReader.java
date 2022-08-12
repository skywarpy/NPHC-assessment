package org.nphc.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import org.nphc.entities.Employee;
import org.nphc.exceptions.DateFormatException;
import org.nphc.exceptions.SalaryFormatException;


public class CSVReader {
	public static String TYPE = "text/csv";
	static String[] HEADERs = { "id", "login", "name", "salary", "startDate" };
	
	
	public static boolean hasCSVFormat(MultipartFile file) {
	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }
	    return true;
	  }
	public static List<Employee> csvToEmployees(InputStream is) throws DateFormatException, SalaryFormatException {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		        CSVParser csvParser = new CSVParser(fileReader,
		        		CSVFormat.DEFAULT.builder().setSkipHeaderRecord(true).setIgnoreHeaderCase(true).setTrim(true).setHeader(HEADERs).setCommentMarker('#').build());) {
			DateFormatterUtil dateFormatterUtil = new DateFormatterUtil();
			SalaryParserUtil salaryParser = new SalaryParserUtil();
			List<Employee> employees = new ArrayList<Employee>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
		      for (CSVRecord csvRecord : csvRecords) {
		    	  Employee employee = new Employee();
		    	  employee.setId(csvRecord.get("id"));
		    	  employee.setLogin(csvRecord.get("login"));
		    	  employee.setName(csvRecord.get("name"));
		    	  employee.setSalary(salaryParser.salary(csvRecord.get("salary")));
		    	  String startDate = csvRecord.get("startDate");
		    	  LocalDate startDateFormatted = dateFormatterUtil.getFormattedDate(startDate);
		    	  employee.setStartDate(startDateFormatted);
		    	  employees.add(employee);
		      }
		      return employees;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		} 
		
	}
	
	
}
