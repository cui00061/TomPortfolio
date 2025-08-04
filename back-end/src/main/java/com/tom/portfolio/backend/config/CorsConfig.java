package com.tom.portfolio.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 允许所有路径
                        .allowedOrigins("http://localhost:5173") // 允许本地前端
                        .allowedMethods("*") // GET, POST, PUT, DELETE...
                        .allowedHeaders("*")
                        .allowCredentials(true); // 如果前端要携带 cookie
            }
        };
    }
}
