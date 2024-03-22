package com.carefirst.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@SpringBootApplication
public class EmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		return BeanFactory.getObjectMapper();
	}
	
	@Bean
	public XmlMapper xmlMapper(){
		return BeanFactory.getXmlMapper();
	}
}
