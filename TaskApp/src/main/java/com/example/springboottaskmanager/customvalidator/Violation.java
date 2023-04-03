package com.example.springboottaskmanager.customvalidator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Класс, описывающий ошибку валидации.
 * {@param fieldName} - название поля, не прошедшего валидацию.
 * {@param message} - сообщение об ошибке.
 */
@Getter
@RequiredArgsConstructor
public class Violation {
    /**
     * Название поля, в котором была допущена ошибка валидации.
     */
    private final String fieldName;
    /**
     * Сообщение об ошибке валидации.
     */
    private final String message;
}
