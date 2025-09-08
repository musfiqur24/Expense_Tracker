package com.expensetracker.service;

import com.expensetracker.model.AppUser;

public interface UserService {
    AppUser saveUser (AppUser user);
    AppUser findByUsername (String username);
}
