package com.example.springboottaskmanager.security.securitymodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    /**
     * Логин пользователя для регистрации.
     */
    private String username;
    /**
     * Пароль пользователя для регистрации.
     */
    private String password;
    /**
     * Имя пользователя при регистрации.
     */
    private String firstname;
    /**
     * Фамилия пользователя при регистрации.
     */
    private String surname;
}
