package com.example.springboottaskmanager.exception.Handlers;

import com.example.springboottaskmanager.exception.Body.NotFoundException;
import com.example.springboottaskmanager.exception.Body.UnknownException;
import com.example.springboottaskmanager.model.CustomError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Класс, обрабатывающий {@link NotFoundException} и {@link UnknownException}.
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    @Value("${debug_mode}")
    private boolean debugMode;

    /**
     * Обработчик {@link NotFoundException}. Возвращает тело ошибки с HTTP статусом, сообщением и описанием ошибки.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundException(NotFoundException exception) {
        CustomError error = new CustomError(exception, HttpStatus.NOT_FOUND);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(CreateErrorString(error));
    }

    /**
     * Обработчик {@link UnknownException}. Возвращает тело ошибки с HTTP статусом, сообщением и описанием ошибки.
     */
    @ExceptionHandler(UnknownException.class)
    public ResponseEntity<Object> unknownException(UnknownException exception) {
        CustomError error = new CustomError(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CreateErrorString(error));
    }

    /**
     * Метод, достающий из модели {@link CustomError} всю информацию в виде строки.
     * @param error - обрабатываемая ошибка.
     * @return - строка с сообщением и описанием ошибки. При DEBUG_MODE true также выводит StackTrace.
     */
    public String CreateErrorString(CustomError error) {
        if(debugMode) {
            return ("Status: " + HttpStatus.NOT_FOUND + "\n" +
                            "Message: " + error.getMessage() + "\n" +
                            "Description: " + error.getDescription() + "\n" +
                            "StackTrace: " + error.getStackTrace());
        }
        else
            return ("Status: " + HttpStatus.NOT_FOUND+ "\n" +
                            "Message: " + error.getMessage() + "\n" +
                            "Description: " + error.getDescription());
    }
}