package com.carefirst.employee.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.carefirst.employee.exception.DatabaseOperationException;
import com.carefirst.employee.exception.ResourceNotFoundException;
import com.carefirst.employee.model.domain.Employee;
import com.carefirst.employee.model.entity.EmployeeEntity;
import com.carefirst.employee.repository.EmployeeRepository;
import com.carefirst.employee.service.EmployeeService;

import static com.carefirst.employee.EmployeeConstants.EMPLOYEE_NOT_FOUND_MESSAGE;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	EmployeeRepository repository;
	
	@Override
	public List<Employee> getAllEmployees() {
		List<EmployeeEntity> employees = repository.findAll();
		
		return employees.stream().map(this::convertToEmployee).collect(Collectors.toList());
	}

	@Override
	public Employee getEmpDetails(Long employeeId) throws ResourceNotFoundException {
		EmployeeEntity employeeEntity = repository.findById(employeeId)
		        .orElseThrow(() -> new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND_MESSAGE, employeeId)));
		    
		
		// Convert the EmployeeEntity to Employee DTO
		return convertToEmployee(employeeEntity);
	}

	@Override
	public Employee addEmployee(Employee employee) {
		employee.setEmployeeId(null);
		EmployeeEntity entity = convertToEmployeeEntity(employee);
		return convertToEmployee(repository.save(entity));
	}

	@Override
	public Employee updateEmployee(Employee employee) throws ResourceNotFoundException {
		Optional<EmployeeEntity> existingEmployeeOptional = repository.findById(employee.getEmployeeId());
	    if (!existingEmployeeOptional.isPresent()) {
	        throw new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND_MESSAGE, employee.getEmployeeId()));
	    }
	    EmployeeEntity entity = convertToEmployeeEntity(employee);
	    
		return convertToEmployee(repository.save(entity));
	}

	@Override
	public void deleteEmployee(Long employeeId) throws ResourceNotFoundException, DatabaseOperationException {
		// Check if the employee exists
	    if (!repository.existsById(employeeId)) {
	        throw new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND_MESSAGE, employeeId));
	    }
	    try {
	        repository.deleteById(employeeId);
	    } catch (DataAccessException e) {
	        throw new DatabaseOperationException("Failed to delete the employee with ID: " + employeeId, e);
	    }
	}
	
	private Employee convertToEmployee(EmployeeEntity employeeEntity) {
        if (employeeEntity == null) {
            return null;
        }
        
        Employee employee = new Employee();
        
        employee.setEmployeeId(employeeEntity.getEmployeeId());
        employee.setFirstName(employeeEntity.getFirstName());
        employee.setLastName(employeeEntity.getLastName());
        employee.setEmailAddress(employeeEntity.getEmailAddress());
        employee.setPhone(employeeEntity.getPhone());
        employee.setBirthDate(employeeEntity.getBirthDate());
        employee.setJobTitle(employeeEntity.getJobTitle());
        employee.setDepartment(employeeEntity.getDepartment());
        employee.setLocation(employeeEntity.getLocation());
        employee.setStartDate(employeeEntity.getStartDate());
        employee.setReportingManager(employeeEntity.getReportingManager());
        
        return employee;
    }
	
	private EmployeeEntity convertToEmployeeEntity(Employee employee) {
        if (employee == null) {
            return null;
        }
        
        EmployeeEntity employeeEntity = new EmployeeEntity();
        
        employeeEntity.setEmployeeId(employee.getEmployeeId());
        employeeEntity.setFirstName(employee.getFirstName());
        employeeEntity.setLastName(employee.getLastName());
        employeeEntity.setEmailAddress(employee.getEmailAddress());
        employeeEntity.setPhone(employee.getPhone());
        employeeEntity.setBirthDate(employee.getBirthDate());
        employeeEntity.setJobTitle(employee.getJobTitle());
        employeeEntity.setDepartment(employee.getDepartment());
        employeeEntity.setLocation(employee.getLocation());
        employeeEntity.setStartDate(employee.getStartDate());
        employeeEntity.setReportingManager(employee.getReportingManager());
        
        return employeeEntity;
    }

}
