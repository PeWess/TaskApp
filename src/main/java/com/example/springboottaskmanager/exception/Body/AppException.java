package com.example.springboottaskmanager.exception.Body;

/**
 * Класс, описывающий ошибку приложения
 */
public class AppException extends RuntimeException{
    public AppException(String message) {
        super(message);
    }
}
