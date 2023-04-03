package com.example.springboottaskmanager.exception.body;

/**
 * Ошибка при запросе несуществующей записи.
 */
public class NotFoundException extends AppException {
    /**
     * @param message Сообщение об ошибке.
     */
    public NotFoundException(final String message) {
        super(message);
    }
}
