// src/main/java/com/tom/portfolio/backend/model/AppUser.java
package com.tom.portfolio.backend.model;

import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter;

@Entity @Table(name="users", uniqueConstraints=@UniqueConstraint(columnNames="username"))
@Getter @Setter
public class AppUser {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=64)
    private String username;

    @Column(nullable=false, length=100) // BCrypt ~60 字符
    private String passwordHash;

    @Column(nullable=false, length=20)
    private String role = "ADMIN";
}
