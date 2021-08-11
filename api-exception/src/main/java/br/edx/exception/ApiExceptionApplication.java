package br.edx.exception;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;

@SpringBootApplication(exclude = {WebMvcAutoConfiguration.class, ServletWebServerFactoryAutoConfiguration.class})
@EnableAutoConfiguration()
public class ApiExceptionApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(ApiExceptionApplication.class, args);
	}

}
