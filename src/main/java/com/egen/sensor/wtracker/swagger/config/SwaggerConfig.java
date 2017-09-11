package com.egen.sensor.wtracker.swagger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.models.dto.builder.ApiInfoBuilder;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

@Configuration
@EnableSwagger
public class SwaggerConfig {
	@Autowired
	private SpringSwaggerConfig springSwaggerConfig;

	@Bean
	public SwaggerSpringMvcPlugin configureSwagger() {
		SwaggerSpringMvcPlugin swaggerSpringMvcPlugin = new SwaggerSpringMvcPlugin(this.springSwaggerConfig);
		ApiInfo apiInfo = new ApiInfoBuilder().title("Weight Tracker REST API")
				.description("WeightTracker Api for creating and reading metrics and alerts")
				.termsOfServiceUrl("http://localhost:8080/weighttracker/swagger-ui/service.html")
				.contact("kasaramch@gmail.com")
				.license("License")
				.licenseUrl("http://localhost:8080/weighttracker/swagger-ui/license.html")
				.build();
		swaggerSpringMvcPlugin.apiInfo(apiInfo)
							  .apiVersion("1.0")
							  .includePatterns("/metrics/*.*", "/alerts/*.*");
							  //.useDefaultResponseMessages(false);
		return swaggerSpringMvcPlugin;
	}
}