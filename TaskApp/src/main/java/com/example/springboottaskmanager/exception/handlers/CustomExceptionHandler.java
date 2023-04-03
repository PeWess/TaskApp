package com.example.springboottaskmanager.exception.handlers;

import com.example.springboottaskmanager.exception.body.NotFoundException;
import com.example.springboottaskmanager.exception.body.UnknownException;
import com.example.springboottaskmanager.exception.body.UserExitsException;
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
    /**
     * Переменная окружения, определяющая необходимость выводить StackTrace.
     */
    @Value("${debug_mode}")
    private boolean debugMode;

    /**
     * Обработчик {@link NotFoundException}.
     * Возвращает тело ошибки с HTTP статусом, сообщением и описанием ошибки.
     * @param exception Ошибка {@link NotFoundException}.
     * @return Сообщение об ошибке.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundException(
            final NotFoundException exception) {
        CustomError error = new CustomError(exception, HttpStatus.NOT_FOUND);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(createErrorString(error, HttpStatus.NOT_FOUND));
    }

    /**
     * Обработчик {@link UnknownException}.
     * Возвращает тело ошибки с HTTP статусом, сообщением и описанием ошибки.
     * @param exception Ошибка {@link UnknownException}.
     * @return Сообщение об ошибке.
     */
    @ExceptionHandler(UnknownException.class)
    public ResponseEntity<Object> unknownException(final UnknownException exception) {
        CustomError error = new CustomError(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorString(error, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * Обработчик {@link UserExitsException}.
     * Возвращает тело ошибки с HTTP статусом, сообщением и описанием ошибки.
     * @param exception Ошибка {@link UserExitsException}.
     * @return Сообщение об ошибке.
     */
    @ExceptionHandler(UserExitsException.class)
    public ResponseEntity<Object> userExistsException(
            final UserExitsException exception) {
        CustomError error = new CustomError(exception, HttpStatus.FORBIDDEN);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(createErrorString(error, HttpStatus.FORBIDDEN));
    }

    /**
     * Метод, достающий из модели {@link CustomError} всю информацию.
     * @param error Обрабатываемая ошибка.
     * @param status Статус, передаваемый с ошибкой.
     * @return Строка с сообщением и описанием ошибки.
     * При DEBUG_MODE true также выводит StackTrace.
     */
    public String createErrorString(
            final CustomError error, final HttpStatus status) {
        if (debugMode) {
            return ("Status: " + status + "\n"
                            + "Message: " + error.getMessage() + "\n"
                            + "Description: " + error.getDescription() + "\n"
                            + "StackTrace: " + error.getStackTrace());
        } else {
            return ("Status: " + status + "\n"
                    + "Message: " + error.getMessage() + "\n"
                    + "Description: " + error.getDescription());
        }
    }
}
