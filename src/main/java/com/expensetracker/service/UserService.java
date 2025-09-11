package com.expensetracker.service;

import com.expensetracker.model.AppUser;

import java.util.Optional;

public interface UserService {
    AppUser saveUser (AppUser user);
    AppUser findByUsername (String username);
    Optional<AppUser> findUserById(Long id);
}
