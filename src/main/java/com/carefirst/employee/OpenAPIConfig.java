package com.carefirst.employee;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

	@Bean
	public OpenAPI myOpenAPI() {
		Server devServer = new Server();
		devServer.setUrl("http://localhost:8090");
		devServer.setDescription("Server URL in Development environment");

		Server prodServer = new Server();
		prodServer.setUrl("http://carefirst.com");
		prodServer.setDescription("Server URL in Production environment");

		Contact contact = new Contact();
		contact.setEmail("harshad@carefirst.com");
		contact.setName("Harshad");
		contact.setUrl("https://www.carefirst.com");

		Info info = new Info().title("Employee Management API").version("1.0.0").contact(contact)
				.description("This API exposes endpoints to manage employees.");
		return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
	}
}
