package com.museo.eventos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI eventosOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Eventos")
                        .version("1.0")
                        .description("Microservicio de eventos para sistema de museo"));
    }
}
