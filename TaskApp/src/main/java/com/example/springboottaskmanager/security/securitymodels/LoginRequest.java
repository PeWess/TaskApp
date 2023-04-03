package com.example.springboottaskmanager.security.securitymodels;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    /**
     * Логин пользователя для авторизации.
     */
    private String username;
    /**
     * Пароль пользователя для авторизации.
     */
    private String password;
}
