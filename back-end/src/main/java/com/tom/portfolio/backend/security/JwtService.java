// ===============================================================
// 文件：JwtService.java
// 位置：src/main/java/com/tom/portfolio/backend/security/
// 作用：JWT 服务类，负责生成和解析 JWT
// 用途：提供签发 Token 和校验 Token 的功能
// ---------------------------------------------------------------
// File: JwtService.java
// Location: src/main/java/com/tom/portfolio/backend/security/
// Role: JWT service class, responsible for generating and parsing JWT
// Usage: Provides functions to issue tokens and validate tokens
// ===============================================================

package com.tom.portfolio.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    private final Key key;         // 签名密钥 / Signing key
    private final long ttlMillis;  // token 有效期（毫秒）/ Token time-to-live in milliseconds

    // 构造函数：从配置文件读取 JWT secret 和 TTL
    // Constructor: load JWT secret and TTL from application properties
    public JwtService(
            @Value("${app.jwt.secret:change-this-please-change-this-32bytes-key}") String secret,
            @Value("${app.jwt.ttlMillis:3600000}") long ttlMillis) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes()); // 使用密钥生成 HMAC SHA key / Generate HMAC SHA key from secret
        this.ttlMillis = ttlMillis;
    }

    // 生成 JWT
    // Generate JWT token
    public String generate(String subject, Map<String, Object> claims) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(subject)              // 设置主体（用户名）/ Set subject (username)
                .addClaims(claims)                // 添加额外信息（如角色）/ Add extra claims (e.g., role)
                .setIssuedAt(new Date(now))       // 签发时间 / Issue time
                .setExpiration(new Date(now + ttlMillis)) // 过期时间 / Expiration time
                .signWith(key, SignatureAlgorithm.HS256)  // 使用 HS256 签名 / Sign with HS256
                .compact();
    }

    // 解析 JWT
    // Parse and validate JWT token
    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)   // 设置签名密钥 / Set signing key
                .build()
                .parseClaimsJws(token); // 解析并返回 claims / Parse and return claims
    }
}
