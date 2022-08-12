package org.nphc;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.nphc.controllers.EmployeeGetController;
import org.nphc.entities.Employee;
import org.nphc.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeGetController.class)
public class EmployeeGetControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;
	
	Employee mockEmployee1 = new Employee("e0001","hpotter","Harry Potter",(new BigDecimal("1234.00").setScale(3)), LocalDate.of(2001, 11, 16));
	Employee mockEmployee2 = new Employee("e0004","rhagrid","Rubeus Hagrid",(new BigDecimal("3999.999").setScale(3)), LocalDate.of(2001, 11, 16));
	Employee mockEmployee3 = new Employee("e0005","Voldemort","Lord Voldemort",(new BigDecimal("523.4").setScale(3)), LocalDate.of(2001, 11, 17));
	Employee mockEmployee4 = new Employee("e0007","hgranger","Hermione Granger",(new BigDecimal("0.0").setScale(3)), LocalDate.of(2001, 11, 18));
	Employee mockEmployee5 = new Employee("e0008","adumbledore","Albus Dumbledore",(new BigDecimal("34.23").setScale(3)), LocalDate.of(2001, 11, 19));
	Employee mockEmployee6 = new Employee("e0010","basilisk","Basilisk",(new BigDecimal("23.43").setScale(3)), LocalDate.of(2001, 11, 21));
	
	@Test
	public void getEmployeeWithCrud() throws Exception {
		Mockito.when(employeeService.getEmployee(Mockito.anyString())).thenReturn(mockEmployee1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/users/e0001").accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		String expected = "{\"id\":\"e0001\",\"login\":\"hpotter\",\"name\":\"Harry Potter\",\"salary\":1234.000,\"startDate\":\"2001-11-16\"}";
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		
	}
	
	
	@Test
	public void getEmployeeListWithParams() throws Exception {
		List<Employee> mockEmployeeList = new ArrayList<Employee>();
		mockEmployeeList.add(mockEmployee5);
		mockEmployeeList.add(mockEmployee6);
		Mockito.when(employeeService.getEmployeesWithLimit(Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(mockEmployeeList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/users/").param("minSalary", "0").param("maxSalary", "1000.00").param("offset","1").param("limit","2").accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		String expected = "{\"results\":[{\"id\":\"e0008\",\"login\":\"adumbledore\",\"name\":\"Albus Dumbledore\",\"salary\":34.230,\"startDate\":\"2001-11-19\"},"
				+ "{\"id\":\"e0010\",\"login\":\"basilisk\",\"name\":\"Basilisk\",\"salary\":23.430,\"startDate\":\"2001-11-21\"}]}";
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	
	@Test
	public void getEmployeeListWithDefaultParams() throws Exception {
		List<Employee> mockEmployeeList = new ArrayList<Employee>();
		mockEmployeeList.add(mockEmployee1);
		mockEmployeeList.add(mockEmployee2);
		mockEmployeeList.add(mockEmployee3);
		mockEmployeeList.add(mockEmployee4);
		mockEmployeeList.add(mockEmployee5);
		mockEmployeeList.add(mockEmployee6);
		Mockito.when(employeeService.getEmployeesWithLimit(Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(mockEmployeeList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/users/").accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		String expected = "{\"results\":[{\"id\":\"e0001\",\"login\":\"hpotter\",\"name\":\"Harry Potter\",\"salary\":1234.000,\"startDate\":\"2001-11-16\"},"
				+ "{\"id\":\"e0004\",\"login\":\"rhagrid\",\"name\":\"Rubeus Hagrid\",\"salary\":3999.999,\"startDate\":\"2001-11-16\"},"
				+ "{\"id\":\"e0005\",\"login\":\"Voldemort\",\"name\":\"Lord Voldemort\",\"salary\":523.400,\"startDate\":\"2001-11-17\"},"
				+ "{\"id\":\"e0007\",\"login\":\"hgranger\",\"name\":\"Hermione Granger\",\"salary\":0.000,\"startDate\":\"2001-11-18\"},"
				+ "{\"id\":\"e0008\",\"login\":\"adumbledore\",\"name\":\"Albus Dumbledore\",\"salary\":34.230,\"startDate\":\"2001-11-19\"},"
				+ "{\"id\":\"e0010\",\"login\":\"basilisk\",\"name\":\"Basilisk\",\"salary\":23.430,\"startDate\":\"2001-11-21\"}]}";
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		
	}
	
	
	
	
	
}
