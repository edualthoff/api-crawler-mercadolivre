package br.ml.gateway.excepetion.config;

import org.apache.logging.log4j.core.config.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Order(value = -1)
public class WebErrorConfiguration {
	
	@Value("${api.version}")
	private String currentApiVersion;
	@Value("${api.sendreport.uri}")
	private String sendReportUri;

	/**
	 * We override the default {@link DefaultErrorAttributes}
	 *
	 * @return A custom implementation of ErrorAttributes
	 */
	@Bean
	public ErrorAttributes errorAttributes() {
		return new ApiErrorAttributes(currentApiVersion, sendReportUri);
	}
}
