/*
package com.example.springboottaskmanager.customValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

//Валидатор статуса задачи
public class StatusValidator implements ConstraintValidator<StatusValid, String> {

    //Допустимые типы статусов
    private final static String[] types = {"Completed", "In completion", "Failed"};

    //Проверка на соответствие разрешенным статусам
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value != null && !value.isEmpty()) {
            return Arrays.stream(types).anyMatch(value::contains);
        }
        return false;
    }
}*/