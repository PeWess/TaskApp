package com.example.springboottaskmanager.exception.body;

/**
 * Класс, описывающий ошибку приложения.
 */
public class AppException extends RuntimeException {
    /**
     * @param message Сообщение об ошибке.
     */
    public AppException(final String message) {
        super(message);
    }
}
