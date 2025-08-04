package com.tom.portfolio.backend.service;

import com.tom.portfolio.backend.model.AdminUser;
import com.tom.portfolio.backend.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminAuthService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    public boolean authenticate(String username, String password) {
        Optional<AdminUser> user = adminUserRepository.findByUsername(username);
        return user.isPresent() && user.get().getPassword().equals(password);
    }
}

