package com.tom.portfolio.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/*
===============================================================
文件：CorsConfig.java
位置：com.tom.portfolio.backend.config
用途：全局跨域资源共享（CORS）配置
作用：
  - 允许前端（如 http://localhost:5173）跨域访问后端 API
  - 配置允许的路径、来源、HTTP 方法与请求头
  - 可选允许携带 Cookie
服务对象：前端 Web 客户端
===============================================================
*/

/*
===============================================================
File: CorsConfig.java
Package: com.tom.portfolio.backend.config
Purpose: Global Cross-Origin Resource Sharing (CORS) configuration
Effects:
  - Allow the frontend (e.g., http://localhost:5173) to access backend APIs across origins
  - Configure allowed paths, origins, HTTP methods and headers
  - Optionally allow cookies to be sent
Serves: Frontend web clients
===============================================================
*/
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 允许所有路径 / Allow all request paths
                        .allowedOrigins("http://localhost:5173") // 允许本地前端 / Allow local frontend
                        .allowedMethods("*") // 允许所有请求方法 / Allow all HTTP methods
                        .allowedHeaders("*") // 允许所有请求头 / Allow all request headers
                        .allowCredentials(true); // 允许携带 cookie / Allow cookies if needed
            }
        };
    }
}
