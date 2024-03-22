package com.carefirst.employee.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
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

import com.carefirst.employee.model.domain.Employee;
import com.carefirst.employee.repository.EmployeeRepository;
import com.carefirst.employee.service.impl.EmployeeServiceImpl;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = EmployeeController.class)
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Mock
    EmployeeRepository employeeRepository;
	
	@MockBean
    EmployeeServiceImpl employeeService;
	
	Employee mockEmployee = null;
	
    @BeforeEach
    public void init() {
    	mockEmployee = new Employee();
    	mockEmployee.setEmployeeId(1L);
    	mockEmployee.setFirstName("FirstName");
    	mockEmployee.setLastName("LastName");
    	mockEmployee.setEmailAddress("test@test.com");
    	mockEmployee.setPhone("1234567890");
    }
    
    @Test
    public void getAllEmployees() throws Exception
    {
    	Mockito.when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(mockEmployee));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employees").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		
		String expected = "[{\"employeeId\":1,\"firstName\":\"FirstName\",\"lastName\":\"LastName\",\"emailAddress\":\"test@test.com\",\"phone\":\"1234567890\"}]";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }
    
    //@Test
    public void addEmployee() throws Exception
    {
    	Mockito.when(employeeService.addEmployee(any())).thenReturn(mockEmployee);
    	
    	String example = "{\"employeeId\":2,\"firstName\":\"FirstName\",\"lastName\":\"LastName\",\"emailAddress\":\"test@test.com\",\"phone\":\"1234567890\"}";
    	
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/employees").accept(MediaType.APPLICATION_JSON).content(example)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		
    }
}
