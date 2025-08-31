// ===============================================================
// 文件：LoginResp.java
// 位置：src/main/java/com/tom/portfolio/backend/dto/
// 作用：登录响应的数据传输对象 (DTO)，返回给前端的认证结果
// 用途：在 AuthController.login() 登录成功时作为 ResponseEntity 返回
// ---------------------------------------------------------------
// File: LoginResp.java
// Location: src/main/java/com/tom/portfolio/backend/dto/
// Role: Data Transfer Object (DTO) for login response,
//       carrying authentication result for the frontend
// Usage: Returned by AuthController.login() inside ResponseEntity
// ===============================================================

package com.tom.portfolio.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResp {
    // 认证后的 JWT 令牌
    // JWT token generated after authentication
    private String token;

    // 用户名
    // Authenticated username
    private String username;

    // 用户角色（如 USER, ADMIN）
    // User role (e.g., USER, ADMIN)
    private String role;

    // 过期时间（毫秒时间戳，epoch millis）
    // Expiration time (epoch millis)
    private long expiresAt;
}
