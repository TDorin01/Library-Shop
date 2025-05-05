package com.example.Library.Shop.security;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI libraryOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Library Shop API")
                        .description("Documentatia API pentru aplicatia de biblioteca")
                        .version("1.0"));
    }
}
