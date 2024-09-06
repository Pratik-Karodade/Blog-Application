package com.springboot.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Blog REST API",
				description = "Spring Boot Blog REST API documentation",
				version = "V1.0",
				contact = @Contact(
						name = "Pratik",
						email = "karodadepratik@gmail.com"
				),
				license = @License(
						name = "Blog Application 2.0"
				)
		),externalDocs = @ExternalDocumentation(
				description = "Blog Application",
		url = "https://github.com/Pratik-Karodade/Blog-Application"
)
)
public class SpringbootBlogRestapiApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestapiApplication.class, args);
	}

}
