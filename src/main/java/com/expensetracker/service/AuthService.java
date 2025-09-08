package com.expensetracker.service;

import com.expensetracker.dto.AppUserDTO;
import com.expensetracker.dto.AuthDTO;
import com.expensetracker.dto.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO registerUser (AppUserDTO appUserDTO);
    AuthResponseDTO loginUser(AuthDTO authDTO);

}
