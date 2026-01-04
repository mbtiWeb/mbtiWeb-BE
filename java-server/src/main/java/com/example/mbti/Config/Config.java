package com.example.mbti.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        servers = {c
                @Server(
                        url = "https://mbti-api-java.soyyoda.com",
                        description = "Production Server (HTTPS)"
                )
        }
)
public class Config {

}