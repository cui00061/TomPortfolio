// ===============================================================
// 文件：JwtAuthFilter.java
// 位置：src/main/java/com/tom/portfolio/backend/security/
// 作用：JWT 认证过滤器，用于在请求到达控制器之前检查并验证 JWT
// 用途：拦截请求 → 提取 Authorization 头 → 校验 Token →
//      如果有效则在 SecurityContext 中设置认证信息
// ---------------------------------------------------------------
// File: JwtAuthFilter.java
// Location: src/main/java/com/tom/portfolio/backend/security/
// Role: JWT Authentication Filter, verifies JWT before requests reach controllers
// Usage: Intercepts request → extracts Authorization header → validates token →
//        sets authentication in SecurityContext if valid
// ===============================================================

package com.tom.portfolio.backend.security;

import com.tom.portfolio.backend.repository.AppUserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends GenericFilter {

    private final JwtService jwtService;          // JWT 服务 / JWT service
    private final AppUserRepository userRepo;     // 用户仓库 / User repository

    public JwtAuthFilter(JwtService jwtService, AppUserRepository userRepo) {
        this.jwtService = jwtService;
        this.userRepo = userRepo;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest r = (HttpServletRequest) req;
        String auth = r.getHeader("Authorization"); // 获取请求头中的 Authorization / Extract Authorization header
        if (StringUtils.hasText(auth) && auth.startsWith("Bearer ")) {
            String token = auth.substring(7); // 去掉 "Bearer " 前缀 / Strip "Bearer " prefix
            try {
                Claims c = jwtService.parse(token).getBody(); // 解析 JWT / Parse JWT
                String username = c.getSubject();            // 获取用户名 / Extract username
                String role = (String) c.get("role");        // 获取角色 / Extract role

                // 可选：确认用户仍存在
                // Optional: ensure user still exists in DB
                userRepo.findByUsername(username).ifPresent(u -> {
                    var authToken = new UsernamePasswordAuthenticationToken(
                            username, null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + role))
                    );
                    // 将认证信息放入上下文
                    // Set authentication into SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                });
            } catch (Exception ignored) {
                // token 无效则视为未登录
                // If token invalid → treat as unauthenticated
            }
        }
        chain.doFilter(req, res); // 放行请求 / Continue filter chain
    }
}
