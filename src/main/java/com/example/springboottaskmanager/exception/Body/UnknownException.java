package com.example.springboottaskmanager.exception.Body;

/**
 * Класс, описывающий любую ошибку приложения, не относящуюся к {@link NotFoundException} или ошибкам валидации
 */
public class UnknownException extends AppException{
    public UnknownException(String message) {
        super(message);
    }
}
