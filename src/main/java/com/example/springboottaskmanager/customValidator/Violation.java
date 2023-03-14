package com.example.springboottaskmanager.customValidator;

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
    private final String fieldName;
    private final String message;
}
