package com.example.springboottaskmanager.customValidator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

//Класс, хранящий в себе данный о каждом нарушении валидации: поле тела запроса и сообщение о допущенной ошибке
@Getter
@RequiredArgsConstructor
public class Violation {
    private final String fieldName;
    private final String message;
}
