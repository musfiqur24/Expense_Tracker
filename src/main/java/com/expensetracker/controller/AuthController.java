package com.expensetracker.controller;

import com.expensetracker.dto.AppUserDTO;
import com.expensetracker.dto.AuthDTO;
import com.expensetracker.dto.AuthResponseDTO;
import com.expensetracker.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDTO> signup(@RequestBody AppUserDTO appUserDTO) {
        AuthResponseDTO response = authService.registerUser(appUserDTO);
        if ("success".equals(response.getMessage())) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }


    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthDTO authDTO) {
        AuthResponseDTO response = authService.loginUser(authDTO);
        if ("success".equals(response.getMessage())) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }

    }


}
