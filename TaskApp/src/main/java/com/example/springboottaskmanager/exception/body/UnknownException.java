package com.example.springboottaskmanager.exception.body;

/**
 * Ошибка приложения, не относящаяся к {@link NotFoundException}
 * или ошибкам валидации.
 */
public class UnknownException extends AppException {
    /**
     * @param message Сообщение об ошибке.
     */
    public UnknownException(final String message) {
        super(message);
    }
}
