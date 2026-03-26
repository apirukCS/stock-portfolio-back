package com.stock_portfolio.stock_portfolio_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false, unique = true, length = 50)
//    private String username;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = true)
    private String name;

//    @Column(nullable = true)
//    private String googleId;

//    @Column(nullable = false)
//    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}
