// ===============================================================
// 文件：AdminAuthService.java
// 位置：src/main/java/com/tom/portfolio/backend/service/
// 作用：提供管理员认证逻辑，负责校验用户名和密码
// 用途：在不直接依赖 Spring Security 的场景下，
//      可通过此 Service 进行独立的用户名密码校验
// ---------------------------------------------------------------
// File: AdminAuthService.java
// Location: src/main/java/com/tom/portfolio/backend/service/
// Role: Provides admin authentication logic, responsible for verifying
//       username and password
// Usage: Can be used to validate user credentials without directly
//        depending on Spring Security authentication flow
// ===============================================================

package com.tom.portfolio.backend.service;

import com.tom.portfolio.backend.repository.AppUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class AdminAuthService {

    // =============================
    // 用户数据仓库 / User Repository
    // 通过 JPA 从数据库查询用户信息
    // Fetch user information from DB via JPA
    // =============================
    private final AppUserRepository userRepo;

    // =============================
    // 密码加密器 / Password Encoder
    // 用于对比明文密码和数据库中的哈希密码
    // Used to compare raw password with hashed password in DB
    // =============================
    private final BCryptPasswordEncoder encoder;

    // 构造器注入依赖 / Constructor injection
    public AdminAuthService(AppUserRepository userRepo, BCryptPasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    // ===================================================
    // 方法：authenticate
    // 作用：根据用户名和明文密码验证用户是否存在且密码正确
    // 返回：true = 验证成功；false = 用户不存在或密码错误
    // ---------------------------------------------------
    // Method: authenticate
    // Role: Verify if a user exists and if the raw password
    //       matches the stored hashed password
    // Return: true = valid user, false = invalid credentials
    // ===================================================
    public boolean authenticate(String username, String rawPassword) {
        return userRepo.findByUsername(username)
                .map(u -> encoder.matches(rawPassword, u.getPasswordHash())) // ← 使用 BCrypt 哈希校验 / Verify with BCrypt hash
                .orElse(false);
    }
}
