package com.tom.portfolio.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/api/admin/**").authenticated()
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.disable())                 // ✅ 替代 csrf().disable()
                .formLogin(form -> form.disable())           // ✅ 替代 formLogin().disable()
                .httpBasic(Customizer.withDefaults());       // ✅ 替代 httpBasic()

        return http.build();
    }
}
