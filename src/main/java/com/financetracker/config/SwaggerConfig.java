package com.financetracker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI financeTrackerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Personal Finance Tracker API")
                        .description("API documentation for the Personal Finance Tracker application.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Pranay Bochkar")
                                .email("bochkarpranay123@gmail.com")
                                .url("https://www.linkedin.com/in/pranay-bochkar-b9226b226"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
