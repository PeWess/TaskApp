package com.example.springboottaskmanager.exception.Body;

//Исключение, сообщающее о том, что искомой задачи нет в базе данных
public class NotFoundException extends AppException{
    public NotFoundException(String message) {
        super(message);
    }

}
