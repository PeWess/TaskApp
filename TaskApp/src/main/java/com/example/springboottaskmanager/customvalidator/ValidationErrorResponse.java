package com.example.springboottaskmanager.customvalidator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Запись, хранящая все ошибки валидации.
 */
@Getter
@RequiredArgsConstructor
public class ValidationErrorResponse {
    /**
     * Список нарушений валидации.
     */
    private final List<Violation> violations;
}
