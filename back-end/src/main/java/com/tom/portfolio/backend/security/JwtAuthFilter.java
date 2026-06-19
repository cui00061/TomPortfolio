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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends GenericFilter {

    private final JwtService jwtService;          // JWT 服务
    private final AppUserRepository userRepo;     // 用户仓库

    public JwtAuthFilter(JwtService jwtService, AppUserRepository userRepo) {
        this.jwtService = jwtService;
        this.userRepo = userRepo;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // --- ✅ 登录接口放行 ---
        if ("/api/auth/login".equals(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        // --- ✅ 方法白名单：OPTIONS / HEAD 放行 ---
        String method = request.getMethod();
        if ("OPTIONS".equals(method) || "HEAD".equals(method)) {
            chain.doFilter(request, response);
            return;
        }

        // --- ✅ 没有 Bearer token：不做认证处理，交由 Spring Security 判定 ---
        String auth = request.getHeader("Authorization");
        if (!StringUtils.hasText(auth) || !auth.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // --- ✅ 有 Token，解析并设置认证信息 ---
        try {
            String token = auth.substring(7); // 去掉 "Bearer "
            Claims c = jwtService.parse(token).getBody();
            String username = c.getSubject();
            String role = (String) c.get("role");

            userRepo.findByUsername(username).ifPresent(u -> {
                var authToken = new UsernamePasswordAuthenticationToken(
                        username, null, List.of(new SimpleGrantedAuthority("ROLE_" + role))
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            });
        } catch (Exception ignored) {
            // token 无效或过期：不报错，继续匿名身份执行
            SecurityContextHolder.clearContext();
        }

        // --- ✅ 放行后续请求 ---
        chain.doFilter(request, response);
    }
}
