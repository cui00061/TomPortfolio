package com.tom.portfolio.backend.controller;

import com.tom.portfolio.backend.service.AdminAuthService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private AdminAuthService authService;

    @PostMapping("/login")
    public boolean login(@RequestBody LoginRequest request) {
        return authService.authenticate(request.getUsername(), request.getPassword());
    }

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }
}
