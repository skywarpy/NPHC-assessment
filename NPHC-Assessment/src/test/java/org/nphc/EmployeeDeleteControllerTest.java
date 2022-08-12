package org.nphc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nphc.controllers.EmployeeDeleteController;
import org.nphc.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeDeleteController.class)
public class EmployeeDeleteControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;
	
	
	@Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception{
		String empId = "e0001";
		willDoNothing().given(employeeService).deleteEmployee(empId);
		
		ResultActions response = mockMvc.perform(delete("/users/{id}", empId));
		
		response.andExpect(status().isOk());
	}
	
	
}
