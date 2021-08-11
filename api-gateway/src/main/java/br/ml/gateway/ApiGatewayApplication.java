package br.ml.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableDiscoveryClient
@EnableConfigurationProperties
@SpringBootApplication()
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	/**
	 	@Bean
	public DiscoveryClientRouteDefinitionLocator discoveryRoutes(ReactiveDiscoveryClient rdc,
	        DiscoveryLocatorProperties dlp) {
	    return new DiscoveryClientRouteDefinitionLocator(rdc, dlp);
	}
	 * @param rdc
	 * @param dlp
	 * @return
	 */

	
}
