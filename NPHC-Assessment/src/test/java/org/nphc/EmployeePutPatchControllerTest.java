package org.nphc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.nphc.controllers.EmployeePutPatchController;
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
@WebMvcTest(EmployeePutPatchController.class)
public class EmployeePutPatchControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;
	
	Employee mockEmployee1 = new Employee("e0001","hpotter","Harry Potter",(new BigDecimal("1234.00").setScale(3)), LocalDate.of(2001, 11, 16));
	Employee mockEmployee2 = new Employee("e0004","rhagrid","Rubeus Hagrid",(new BigDecimal("3999.999").setScale(3)), LocalDate.of(2001, 11, 16));
	String putJson = "{\"id\":\"e0001\",\"name\":\"HarryPotter\",\"login\":\"hpotterisdead\",\"salary\":1234.00,\"startDate\":\"01-Nov-16\"}";
	
	@Test
	public void testPutEmployee() throws Exception {
		String empId = "e0001";
		Mockito.when(employeeService.getEmployee(Mockito.any())).thenReturn(new Employee());
		Mockito.when(employeeService.addEmployee(Mockito.any(Employee.class))).thenReturn(mockEmployee1);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/"+empId).accept(MediaType.APPLICATION_JSON).content(putJson)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		
	}
	
	
	
	
	
	
}
