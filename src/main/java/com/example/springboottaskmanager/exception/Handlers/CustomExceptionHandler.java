package com.example.springboottaskmanager.exception.Handlers;

import com.example.springboottaskmanager.exception.Body.NotFoundException;
import com.example.springboottaskmanager.exception.Body.UnknownException;
import com.example.springboottaskmanager.model.CustomError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    private boolean debugMode = Boolean.parseBoolean(System.getenv("DEBUG_MODE"));

    //Обработчик исключения ненайденной задачи в базе даных. Возвращает тело ошибки с HTTP статусом, сообщением и описанием ошибки.
    //Переменная среды DEBUG_MODE определяет, будет ли выводиться stacktrace ошибки.
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundException(NotFoundException exception) {
        CustomError error = new CustomError(exception, HttpStatus.NOT_FOUND);
        if(debugMode) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Status: " + HttpStatus.NOT_FOUND + "\n" +
                            "Message: " + error.getMessage() + "\n" +
                            "Description: " + error.getDescription() + "\n" +
                            "StackTrace: " + error.getStackTrace());
        }
        else
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Status: " + HttpStatus.NOT_FOUND+ "\n" +
                            "Message: " + error.getMessage() + "\n" +
                            "Description: " + error.getDescription());
    }

    //Обработчки всех исключений, не считая NotFoundException и исключений валидации. Возвращает HTTP статус 500.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomError> unknownException(UnknownException exception) {
        CustomError error = new CustomError(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}