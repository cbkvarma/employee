package com.carefirst.employee.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carefirst.employee.exception.DatabaseOperationException;
import com.carefirst.employee.exception.MismatchedParametersException;
import com.carefirst.employee.exception.ResourceNotFoundException;
import com.carefirst.employee.model.domain.Employee;
import com.carefirst.employee.service.EmployeeService;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import static com.carefirst.employee.EmployeeConstants.OPERATION_NAME;

@RestController
@RequestMapping("/employees")
@Tag(name = "Employee Management System", description = "Operations pertaining to employee in Employee Management System")

public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	EmployeeService employeeService;

	@Operation(summary = "View a list of available employees", 
				description = "Get all employees. The response is list of All employees")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404") })
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<Employee>> getAllEmployees(HttpServletRequest httpServletRequest) {
		logger.info(OPERATION_NAME, "getAllEmployees");
		List<Employee> employees = this.employeeService.getAllEmployees();

		if (CollectionUtils.isEmpty(employees)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(employees, HttpStatus.OK);
	}
	
	@Operation(summary = "Get an employee by Id")
	@GetMapping(value = "/{employeeId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Employee> getEmployee(
			@Parameter( description = "Employee id from which employee object will retrieve", required = true) @PathVariable("employeeId") Long employeeId,
			HttpServletRequest httpServletRequest) throws ResourceNotFoundException {
		logger.info(OPERATION_NAME, "getEmployee");
		Employee employee = this.employeeService.getEmpDetails(employeeId);

		if (employee == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}
	
	@Operation(summary = "Add an employee")
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Employee> createEmployee(@RequestBody @Valid Employee employee,
			HttpServletRequest httpServletRequest) {
		logger.info(OPERATION_NAME, "createEmployee");

		return new ResponseEntity<>(this.employeeService.addEmployee(employee), HttpStatus.CREATED);
	}
	
	@Operation(summary = "Update an employee")
	@PutMapping(value = "/{employeeId}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Employee> updateEmployee(@PathVariable("employeeId") Long employeeId,
			@RequestBody @Validated Employee employee, HttpServletRequest httpServletRequest) throws ResourceNotFoundException {
		logger.info(OPERATION_NAME, "updateEmployee");

		if (employeeId != employee.getEmployeeId()) {
			throw new MismatchedParametersException();
		}

		return new ResponseEntity<>(this.employeeService.updateEmployee(employee), HttpStatus.OK);
	}

	@Operation(summary = "Delete an employee")
	@DeleteMapping(value = "/{employeeId}")
	public ResponseEntity<Void> deleteEmployee(
			@Parameter(description = "Employee Id from which employee object will delete from database table", required = true) @PathVariable("employeeId") Long employeeId)
			throws ResourceNotFoundException, DatabaseOperationException {
		logger.info(OPERATION_NAME, "deleteEmployee");
		employeeService.deleteEmployee(employeeId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
