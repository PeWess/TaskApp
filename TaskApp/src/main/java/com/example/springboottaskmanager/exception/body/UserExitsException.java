package com.example.springboottaskmanager.exception.body;

/**
 * Ошибка при регистрации пользователя с уже существующим в базе данных логин.
 */
public class UserExitsException extends AppException {
    /**
     * @param message Сообщение об ошибке.
     */
    public UserExitsException(final String message) {
        super(message);
    }

}
