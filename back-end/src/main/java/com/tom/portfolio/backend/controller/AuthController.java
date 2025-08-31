// src/main/java/com/tom/portfolio/backend/controller/AuthController.java
package com.tom.portfolio.backend.controller;

import com.tom.portfolio.backend.dto.LoginDTO;
import com.tom.portfolio.backend.dto.LoginResp;
import com.tom.portfolio.backend.model.AppUser;
import com.tom.portfolio.backend.repository.AppUserRepository;
import com.tom.portfolio.backend.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/*
=====================================================================
文件：AuthController.java
位置：com.tom.portfolio.backend.controller
用途：处理用户认证相关的请求（主要是登录）
作用：
  - 接收前端传来的用户名和密码
  - 使用 Spring Security 验证用户身份
  - 生成并返回 JWT 令牌，包含角色等信息
服务对象：前端用户登录请求
=====================================================================

File: AuthController.java
Package: com.tom.portfolio.backend.controller
Purpose: Handle user authentication requests (mainly login)
Effects:
  - Accept username and password from the frontend
  - Authenticate user credentials with Spring Security
  - Generate and return a JWT token including role info
Serves: Frontend user login requests
=====================================================================
*/
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager am;          // 认证管理器 / Authentication manager
    private final AppUserRepository repo;            // 用户仓库 / User repository
    private final JwtService jwt;                    // JWT 服务 / JWT service
    private final BCryptPasswordEncoder encoder;     // 密码加密器 / Password encoder

    public AuthController(AuthenticationManager am, AppUserRepository repo, JwtService jwt, BCryptPasswordEncoder encoder) {
        this.am = am; this.repo = repo; this.jwt = jwt; this.encoder = encoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResp> login(@RequestBody @Valid LoginDTO dto) {
        // 认证用户名和密码 / Authenticate username and password
        Authentication auth = am.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        // 查询用户信息 / Find user by username
        AppUser u = repo.findByUsername(dto.getUsername()).orElseThrow();

        // 生成 JWT，包含角色信息 / Generate JWT with role info
        String token = jwt.generate(u.getUsername(), Map.of("role", u.getRole()));

        // 设置过期时间，与 JwtService 配置保持一致 / Set expiration time, aligned with JwtService TTL
        long exp = System.currentTimeMillis() + 3600_000;

        // 返回登录响应（令牌 + 用户信息）/ Return login response (token + user info)
        return ResponseEntity.ok(new LoginResp(token, u.getUsername(), u.getRole(), exp));
    }
}
