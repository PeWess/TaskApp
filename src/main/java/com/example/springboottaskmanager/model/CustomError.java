package com.example.springboottaskmanager.model;

import com.example.springboottaskmanager.exception.Body.AppException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

@Getter
public class CustomError {
    private String message;
    private String description;
    private String stackTrace;

    //
    public CustomError (AppException exception, HttpStatus status) {
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        String stacktrace = sw.toString();

        message = exception.getMessage();
        description = Map.of(HttpStatus.NOT_FOUND, "Данной записи нет в базе данных",
                HttpStatus.BAD_REQUEST, "Запрос не соответствует требованиям",
                HttpStatus.INTERNAL_SERVER_ERROR, "Произошла непредвиденная ошибка").get(status);
        stackTrace = stacktrace;
    }
}
