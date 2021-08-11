package br.api.rev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


@EnableJpaAuditing
//@EnableDiscoveryClient
@EnableConfigurationProperties
@OpenAPIDefinition(info = @Info(title = "Revenda API", version = "1.0", description = "Documentation Revenda API v1.0"))
@ComponentScan(basePackages = {"br.edx.exception.*", "br.api.rev.*"})
@SpringBootApplication
public class ApiRevendaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRevendaApplication.class, args);
	}

}
