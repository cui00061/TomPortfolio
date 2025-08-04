package com.tom.portfolio.backend.repository;

import com.tom.portfolio.backend.model.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AdminUserRepository extends JpaRepository<AdminUser,Integer> {
    Optional<AdminUser> findByUsername(String username);
}
