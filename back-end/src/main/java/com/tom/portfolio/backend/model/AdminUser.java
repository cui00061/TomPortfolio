package com.tom.portfolio.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
}
