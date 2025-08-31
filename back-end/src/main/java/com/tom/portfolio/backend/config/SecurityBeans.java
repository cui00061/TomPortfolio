package com.tom.portfolio.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
===============================================================
文件：SecurityBeans.java
用途：注册安全相关的简单 Bean（例如密码加密器）
作用：向 Spring 容器提供 BCryptPasswordEncoder Bean
服务对象：需要注入加密器的组件（如 AuthController）
---------------------------------------------------------------
File: SecurityBeans.java
Purpose: Register simple security-related beans (e.g., encoder)
Effect: Provides a BCryptPasswordEncoder bean to the container
Serves: Components that inject the encoder (e.g., AuthController)
===============================================================
*/
@Configuration
public class SecurityBeans {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
