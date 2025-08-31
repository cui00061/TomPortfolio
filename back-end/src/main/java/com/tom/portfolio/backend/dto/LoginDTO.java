// ===============================================================
// 文件：LoginDTO.java
// 位置：src/main/java/com/tom/portfolio/backend/dto/
// 作用：用于登录请求的数据传输对象 (DTO)，接收用户名和密码
// 用途：在 AuthController.login() 方法中作为 @RequestBody 使用
// ---------------------------------------------------------------
// File: LoginDTO.java
// Location: src/main/java/com/tom/portfolio/backend/dto/
// Role: Data Transfer Object (DTO) for login request,
//       carrying username and password
// Usage: Used in AuthController.login() as @RequestBody
// ===============================================================

package com.tom.portfolio.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginDTO {

    @NotBlank
    @Size(min = 3, max = 64)
    // 用户名：必填，长度 3–64
    // Username: required, length between 3–64
    private String username;

    @NotBlank
    @Size(min = 6, max = 72) // BCrypt 建议上限 72 / BCrypt recommended max length 72
    // 密码：必填，长度 6–72
    // Password: required, length between 6–72
    private String password;
}
