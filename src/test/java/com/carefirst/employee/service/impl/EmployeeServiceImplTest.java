package com.carefirst.employee.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.carefirst.employee.exception.DatabaseOperationException;
import com.carefirst.employee.exception.ResourceNotFoundException;
import com.carefirst.employee.model.domain.Employee;
import com.carefirst.employee.model.entity.EmployeeEntity;
import com.carefirst.employee.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeServiceImpl service;

    private EmployeeEntity employeeEntity;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employeeEntity = new EmployeeEntity();
        employeeEntity.setEmployeeId(1L);
        employeeEntity.setFirstName("FirstName");
        employeeEntity.setLastName("LastName");
        employeeEntity.setEmailAddress("test@test.com");
        employeeEntity.setPhone("1234567890");

        employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setFirstName("FirstName");
        employee.setLastName("LastName");
        employee.setEmailAddress("test@test.com");
        employee.setPhone("1234567890");
    }

    @Test
    void getAllEmployees_ReturnsListOfEmployees() {
        when(repository.findAll()).thenReturn(Arrays.asList(employeeEntity));

        List<Employee> result = service.getAllEmployees();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(employee.getFirstName(), result.get(0).getFirstName());
    }
    
    @Test
    void getEmployee_ReturnsEmployee() throws ResourceNotFoundException {
        when(repository.findById(1L)).thenReturn(Optional.of(employeeEntity));

        Employee result = service.getEmpDetails(1L);

        assertNotNull(result);
        assertEquals(employee.getFirstName(), result.getFirstName());
    }
    
    @Test
    void addEmployee_ReturnsSavedEmployee() {
        when(repository.save(any(EmployeeEntity.class))).thenReturn(employeeEntity);

        Employee result = service.addEmployee(employee);

        assertNotNull(result);
        assertEquals(employee.getFirstName(), result.getFirstName());
    }
    
    @Test
    void updateEmployee_ReturnsUpdatedEmployee() throws ResourceNotFoundException {
        Employee updatedInfo = new Employee();
        updatedInfo.setEmployeeId(1L); 
        updatedInfo.setFirstName("UpdatedFirstName");
        updatedInfo.setLastName("UpdatedLastName");
        updatedInfo.setEmailAddress("updated@test.com");
        
        EmployeeEntity updatedEntity = new EmployeeEntity();
        updatedInfo.setEmployeeId(1L); 
        updatedEntity.setFirstName("UpdatedFirstName");
        updatedEntity.setLastName("UpdatedLastName");
        updatedEntity.setEmailAddress("updated@test.com");

        when(repository.findById(1L)).thenReturn(Optional.of(employeeEntity)); 
        when(repository.save(any(EmployeeEntity.class))).thenReturn(updatedEntity);

        Employee result = service.updateEmployee(updatedInfo);

        assertNotNull(result);
        assertEquals(updatedInfo.getFirstName(), result.getFirstName());
        verify(repository).save(any(EmployeeEntity.class)); 
    }
    
    @Test
    void deleteEmployee_SuccessfulDeletion() throws ResourceNotFoundException, DatabaseOperationException {
        when(repository.existsById(1L)).thenReturn(true); 

        assertDoesNotThrow(() -> service.deleteEmployee(1L));
        verify(repository).deleteById(1L);
    }
    
    @Test
    void deleteEmployee_ThrowsExceptionIfEmployeeNotFound() {
        when(repository.existsById(2L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.deleteEmployee(2L));
    }

}
