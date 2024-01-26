package com.blog.app.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Blogging Application Backend")
						.description("Spring Boot Blogging Application Swagger Api Documentation").version("v0.0.1")
						.license(new License().name("License").url("http://license.org"))
						.contact(new Contact().name("Yash").email("syash832@gmail.com").url("yash.google.com")))
				.externalDocs(new ExternalDocumentation().description("Blogging Application Documentation")
						.url("https://springshop.wiki.github.org/docs"));
	}

}
