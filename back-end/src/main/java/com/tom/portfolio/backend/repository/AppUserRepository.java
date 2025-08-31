// src/main/java/com/tom/portfolio/backend/repository/AppUserRepository.java
package com.tom.portfolio.backend.repository;

import com.tom.portfolio.backend.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
}
