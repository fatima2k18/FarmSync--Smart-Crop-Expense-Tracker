package com.example.FarmSyncProject.config;

import io.swagger.v3.oas.models.info.Info; // ✅ CORRECT — this is the one OpenAPI uses
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("FarmSync API")
                        .description("API documentation for FarmSync project")
                        .version("v1.0"));
    }

}
