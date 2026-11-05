package com.projeto.StudyTracker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("StudyTracker API")
                        .description("API para gerenciamento e rastreamento de estudos, matérias e sessões de estudo.")
                        .version("1.0.0"));
    }
}
