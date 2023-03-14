package com.example.springboottaskmanager.exception.Body;

/**
 * Класс, описывающий ошибку, вызываемую при запросе несуществующей записи
 */
public class NotFoundException extends AppException{
    public NotFoundException(String message) {
        super(message);
    }

}
