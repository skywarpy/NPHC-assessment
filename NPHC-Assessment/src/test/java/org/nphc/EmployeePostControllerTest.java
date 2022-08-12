package org.nphc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.nphc.controllers.EmployeePostController;
import org.nphc.entities.Employee;
import org.nphc.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeePostController.class)
public class EmployeePostControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;
	
	Employee mockEmployee1 = new Employee("e00014","hpotterisdead","Harry Potter",(new BigDecimal("1234.00").setScale(3)), LocalDate.of(2001, 11, 16));
	
	String expectedJson = "{\"id\":\"e0014\",\"name\":\"HarryPotter\",\"login\":\"hpotterisdead\",\"salary\":1234.00,\"startDate\":\"01-Nov-16\"}";
	
	@Test
	public void createEmployee() throws Exception {
		
		
		Mockito.when(employeeService.addEmployee(Mockito.any(Employee.class))).thenReturn(mockEmployee1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/users/")
				.accept(MediaType.APPLICATION_JSON).content(expectedJson)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}
}
