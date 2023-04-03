package com.example.springboottaskmanager.security.securitymodels;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponse {
    /**
     * Токен, возвращаемый при регистрации/авторизации.
     */
    private String token;
}
