package br.ml.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

//@SpringBootApplication(scanBasePackages = { "br.ml.api", "br.edx.exception"}, exclude={DataSourceAutoConfiguration.class})
//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
//@EntityListeners(AuditingEntityListener.class)
@OpenAPIDefinition(info = @Info(title = "Mercadolivre Service API", version = "1.0", description = "Documentation Mercadolivre API v1.0"))
@EnableScheduling
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
//@EnableDiscoveryClient
@EnableConfigurationProperties
@ComponentScan(basePackages = {"br.edx.exception.*", "br.ml.api.*"})
@SpringBootApplication
public class MercadolivreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MercadolivreApplication.class, args);
	}

}
