package com.example.springboottaskmanager.model;

import com.example.springboottaskmanager.exception.body.AppException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * Модель данных ошибки.
 * {@param message} - сообщние ошибки.
 * {@param description} - описание ошибки.
 * {@param stackTrace} - stacktrace ошибки.
 */
@Getter
public class CustomError {
    /**
     * Сообщение об оишбке.
     */
    private final String message;
    /**
     * Описание ошибки.
     */
    private final String description;
    /**
     * StackTrace ошибки.
     */
    private final String stackTrace;

    /**
     * @param exception Пойманное исклчение.
     * @param status HTTP-статус
     */
    public CustomError(final AppException exception, final HttpStatus status) {
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        String stacktrace = sw.toString();

        message = exception.getMessage();
        description = Map.of(HttpStatus.NOT_FOUND, "Данной записи нет в базе данных",
                HttpStatus.BAD_REQUEST, "Запрос не соответствует требованиям",
                HttpStatus.INTERNAL_SERVER_ERROR, "Произошла непредвиденная ошибка",
                HttpStatus.FORBIDDEN, "Пользователь с данным ником уже существует").get(status);
        stackTrace = stacktrace;
    }
}
