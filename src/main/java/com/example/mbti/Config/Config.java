package com.example.mbti.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class Config {

    @Bean
    public OpenAPI customOpenAPI() {
        // Swagger UI에서 모든 API 호출 시 이 주소를 기반으로 요청을 보냅니다.
        Server server = new Server();
        server.setUrl("https://mbti-api-java.soyyoda.com");
        server.setDescription("Production Server (HTTPS)");

        return new OpenAPI().servers(List.of(server));
    }
}
