// ===============================================================
// 文件：BootstrapAdmin.java
// 位置：src/main/java/com/tom/portfolio/backend/
// 作用：在开发环境启动时，自动初始化一个管理员账号
// 用途：避免手动插入管理员数据，保证至少存在一个管理员
// ---------------------------------------------------------------
// File: BootstrapAdmin.java
// Location: src/main/java/com/tom/portfolio/backend/
// Role: Automatically initializes an admin user when running
//       in the development profile
// Usage: Ensures there is always at least one admin user without
//        needing to insert data manually
// ===============================================================

package com.tom.portfolio.backend;

import com.tom.portfolio.backend.model.AppUser;
import com.tom.portfolio.backend.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Profile("dev") // 仅在开发环境启用 / Only active in "dev" profile
@Configuration
public class BootstrapAdmin {

    // ===================================================
    // 方法：seedAdmin
    // 作用：在应用启动时检查是否已有管理员账号；
    //      如果没有则创建一个默认管理员
    // ---------------------------------------------------
    // Method: seedAdmin
    // Role: On application startup, checks if an admin user
    //       exists; if not, creates a default admin account
    // ===================================================
    @Bean
    CommandLineRunner seedAdmin(AppUserRepository repo, BCryptPasswordEncoder enc) {
        return args -> {
            // ===============================
            // 从环境变量获取管理员账号与密码
            // 若未设置，则使用默认值
            // Get admin username & password from environment variables
            // Fallback to defaults if not provided
            // ===============================
            String user = System.getenv().getOrDefault("ADMIN_USER","admin");
            String pass = System.getenv().getOrDefault("ADMIN_PASS","yA046861....");

            // ===============================
            // 如果数据库中不存在该用户名，则创建新管理员
            // If username does not exist in DB, create new admin
            // ===============================
            if (repo.findByUsername(user).isEmpty()) {
                AppUser u = new AppUser();
                u.setUsername(user);
                u.setPasswordHash(enc.encode(pass)); // 密码加密存储 / Store password as BCrypt hash
                u.setRole("ADMIN");                  // 设置角色为管理员 / Set role to ADMIN
                repo.save(u);                        // 保存到数据库 / Save to DB
            }
        };
    }
}
