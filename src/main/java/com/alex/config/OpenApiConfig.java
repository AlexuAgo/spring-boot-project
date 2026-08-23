package com.alex.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI baseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Software Engineer API")
                        .version("1.0.0")
                        .description("Spring Boot REST API for managing software engineers.")
                        .contact(new Contact()
                                .name("Alexandros Agko")
                                .email("alexandrosagko@gmail.com")
                                .url("https://github.com/AlexuAgo"))
                );
    }
}