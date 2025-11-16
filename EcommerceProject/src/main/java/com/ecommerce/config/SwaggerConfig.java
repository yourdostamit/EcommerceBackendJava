package com.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenApI() {
		return new OpenAPI().info(new Info().title("ecommerce prastice projecct")
				.description("shopease toutourlas explanation")
				.version("1.o")
				.contact(new Contact().name("the code revel"))
				);
	}
	
}
