package br.ml.gateway.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springframework.boot.info.BuildProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;


@Configuration
public class OpenApiDocument {

	

 	//@Autowired
	//private RouteDefinitionLocator locator;

 	@Bean
	public List<GroupedOpenApi> apis(RouteDefinitionLocator locator, SwaggerUiConfigProperties swaggerUiConfig) {
 		SwaggerUiConfigProperties.setSwaggerUrls(new HashSet<>());
		List<GroupedOpenApi> groups = new ArrayList<>();
		List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
		definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*-service"))
				.forEach(routeDefinition -> {
					// String name = routeDefinition.getId().replaceAll("-service", "");
					String id = routeDefinition.getId();
					if (!id.toLowerCase().contains("ReactiveCompositeDiscoveryClient".toLowerCase())) {
						String name = id.split("-")[0];
						groups.add(GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").setGroup(name).build());
					}
				});
		return groups;
	}

 	
	@Bean
	public OpenAPI customOpenAPI(BuildProperties buildProperties) {
	    return new OpenAPI()
	            .components(new Components()
	                .addSecuritySchemes("basicScheme", new SecurityScheme()
	                    .type(SecurityScheme.Type.HTTP).scheme("basic")))
	            .info(new Info()
	                .title("Petstore API")
	                .version(buildProperties.getVersion())
	                .description(
	                    "This is a sample server Petstore server. You can find out more about Swagger at [http://swagger.io](http://swagger.io) or on [irc.freenode.net, #swagger](http://swagger.io/irc/). For this sample, you can use the api key `special-key` to test the authorization filters.")
	                    .termsOfService("http://swagger.io/terms/")
	                    .license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}


}
