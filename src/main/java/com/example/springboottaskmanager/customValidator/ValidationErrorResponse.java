package com.example.springboottaskmanager.customValidator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Запись, хранящая все ошибки валидации.
 */
@Getter
@RequiredArgsConstructor
public class ValidationErrorResponse {
    private final List<Violation> violations;
}
