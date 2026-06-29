package com.museo.exposiciones.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI exposicionesOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Exposiciones")
                        .version("1.0")
                        .description("Microservicio para gestión de exposiciones de museos"));
    }
}