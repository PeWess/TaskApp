package com.example.springboottaskmanager.exception.handlers;

import com.example.springboottaskmanager.customvalidator.ValidationErrorResponse;
import com.example.springboottaskmanager.customvalidator.Violation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, обрабатывающий {@link MethodArgumentNotValidException}.
 */
@RestControllerAdvice
public class ValidationExceptionHandler {
    /**
     * Метод, обрабатывающий все ошибки валидации.
     * @param e Ошибка {@link MethodArgumentNotValidException}
     * @return Список нарушений валидации {@link ValidationErrorResponse}
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onMethodArgumentNotValidException(
            final MethodArgumentNotValidException e
    ) {
        final List<Violation> violations = e.getBindingResult()
                .getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }
}
