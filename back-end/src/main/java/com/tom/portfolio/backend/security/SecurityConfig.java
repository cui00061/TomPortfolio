// ===============================================================
// 文件：SecurityConfig.java
// 位置：src/main/java/com/tom/portfolio/backend/security/
// 作用：Spring Security 核心配置类，定义安全策略、过滤器链、用户认证方式
// 用途：配置密码加密、用户加载方式、JWT 过滤器、安全规则（哪些接口需要认证/角色）
// ---------------------------------------------------------------
// File: SecurityConfig.java
// Location: src/main/java/com/tom/portfolio/backend/security/
// Role: Core Spring Security configuration class, defines security policies,
//       filter chain, and user authentication method
// Usage: Configure password encoding, user loading, JWT filter, and security rules
// ===============================================================

package com.tom.portfolio.backend.security;

import com.tom.portfolio.backend.model.AppUser;
import com.tom.portfolio.backend.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // =============================
    // 密码加密器 / Password Encoder
    // 使用 BCrypt 进行密码哈希存储
    // Use BCrypt for password hashing
    // =============================
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // =============================
    // 用户加载服务 / UserDetailsService
    // 从数据库加载用户信息（用户名、密码、角色）
    // Load user details from DB (username, password, roles)
    // =============================
    @Bean
    UserDetailsService userDetailsService(AppUserRepository repo) {
        return username -> {
            AppUser u = repo.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return User.withUsername(u.getUsername())
                    .password(u.getPasswordHash()) // 已加密密码 / Encoded password
                    .roles(u.getRole())            // Spring 会自动加前缀 "ROLE_"
                    .build();
        };
    }

    // =============================
    // 认证管理器 / Authentication Manager
    // 提供认证服务，供 AuthController 登录时使用
    // Provides authentication service (used in AuthController login)
    // =============================
    @Bean
    AuthenticationManager authenticationManager(
            org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration cfg
    ) throws Exception {
        return cfg.getAuthenticationManager();
    }

    // =============================
    // 安全过滤器链 / Security Filter Chain
    // 定义安全规则：哪些请求放行，哪些需要认证
    // Define security rules: which requests are public, which need authentication
    // =============================
    @Bean
    SecurityFilterChain filter(HttpSecurity http, JwtAuthFilter jwt) throws Exception {
        http.csrf(csrf -> csrf.disable()) // 关闭 CSRF / Disable CSRF (for API usage)
                .cors(Customizer.withDefaults()) // 启用 CORS / Enable CORS
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 使用无状态会话 / Stateless session (JWT)
                .authorizeHttpRequests(auth -> auth
                        // 预检请求 / Preflight requests
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // ✅ 联系表单匿名提交放行 / Allow anonymous contact form submission
                        .requestMatchers(HttpMethod.POST, "/api/contact").permitAll()

                        // 公开读取接口 / Public GET endpoints
                        .requestMatchers(HttpMethod.GET,
                                "/api/projects/**", "/api/contact/**", "/api/about/**").permitAll()

                        // 登录接口放行 / Allow login endpoint
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

                        // 其它写操作需要管理员角色 / Other write operations require ADMIN role
                        .requestMatchers("/api/projects/**", "/api/contact/**", "/api/about/**").hasRole("ADMIN")

                        // About 模块特殊规则 / Special rules for About module
                        .requestMatchers(HttpMethod.GET, "/api/about").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/about").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/about").hasRole("ADMIN")

                        // 其它请求默认允许（可按需收紧）
                        // All other requests are allowed (can be restricted as needed)
                        .anyRequest().permitAll()
                )
                // 在 BasicAuthenticationFilter 之前加入 JWT 过滤器
                // Add JWT filter before BasicAuthenticationFilter
                .addFilterBefore(jwt, BasicAuthenticationFilter.class);

        return http.build();
    }

}
