package com.carefirst.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carefirst.employee.model.entity.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

}
