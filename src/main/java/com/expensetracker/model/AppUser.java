package com.expensetracker.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullName;
    @Column(unique = true)
    private String username;
    private String password;

}
