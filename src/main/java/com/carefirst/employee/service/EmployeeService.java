package com.carefirst.employee.service;

import java.util.List;

import com.carefirst.employee.exception.DatabaseOperationException;
import com.carefirst.employee.exception.ResourceNotFoundException;
import com.carefirst.employee.model.domain.Employee;

public interface EmployeeService {
	
	List<Employee> getAllEmployees();
	
	Employee getEmpDetails(Long employeeId) throws ResourceNotFoundException;
	
	Employee addEmployee(Employee employee);
	
	Employee updateEmployee(Employee employee) throws ResourceNotFoundException;
	
	void deleteEmployee(Long employeeId) throws ResourceNotFoundException, DatabaseOperationException;
	
}
