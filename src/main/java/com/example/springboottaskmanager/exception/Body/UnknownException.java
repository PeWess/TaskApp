package com.example.springboottaskmanager.exception.Body;

//Исключение, сообщающее о любой ошибке, отличной от ConstraintViolationException или NotFoundException
public class UnknownException extends AppException{
    public UnknownException(String message) {
        super(message);
    }
}
