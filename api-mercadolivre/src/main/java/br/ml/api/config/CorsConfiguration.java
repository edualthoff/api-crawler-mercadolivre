package br.ml.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("https://back.revendacerta.com.br").allowedMethods("GET", "POST",
				"PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT")
		.allowedHeaders("x-requested-with", "Content-Type", "origin", "authorization",
				"Authorization", "accept", "client-security-token", "Access-Control-Allow-Origin", "credential", "X-XSRF-TOKEN",
				"X-Forwarded-For", "X-Forwarded-Proto, X-Forwarded-Host").maxAge(3600);

		registry.addMapping("/**").allowedOrigins("http://back.revendacerta.com.br").allowedMethods("GET", "POST",
				"PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT")
		.allowedHeaders("x-requested-with", "Content-Type", "origin", "authorization",
				"Authorization", "accept", "client-security-token", "Access-Control-Allow-Origin", "credential", "X-XSRF-TOKEN",
				"X-Forwarded-For", "X-Forwarded-Proto, X-Forwarded-Host").maxAge(3600);

		registry.addMapping("/**").allowedOrigins("https://app.revendacerta.com.br").allowedMethods("GET", "POST",
				"PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT")
		.allowedHeaders("x-requested-with", "Content-Type", "origin", "authorization",
				"Authorization", "accept", "client-security-token", "Access-Control-Allow-Origin", "credential", "X-XSRF-TOKEN",
				"X-Forwarded-For", "X-Forwarded-Proto, X-Forwarded-Host").maxAge(3600);
		
		/** Não Precisa
		registry.addMapping("/**").allowedOrigins("http://gateway-service:9020").allowedMethods("GET", "POST",
				"PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT")
		.allowedHeaders("x-requested-with", "Content-Type", "origin", "authorization",
				"Authorization", "accept", "client-security-token", "Access-Control-Allow-Origin", "credential", "X-XSRF-TOKEN",
				"X-Forwarded-For", "X-Forwarded-Proto, X-Forwarded-Host").maxAge(3600);
		*/

	}
}
