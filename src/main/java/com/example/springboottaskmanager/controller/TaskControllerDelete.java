package com.example.springboottaskmanager.controller;

import com.example.springboottaskmanager.aspect.Auditable;
import com.example.springboottaskmanager.exception.Body.UnknownException;
import com.example.springboottaskmanager.service.TaskServiceDelete;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks/")
public class TaskControllerDelete {
    @Autowired
    TaskServiceDelete taskService;

    //Метод, выполняющий проверку корректности введенного ID(проверяет, является ли числом) в параметрах запроса и посылающий запрос в сервис на удаление задачи
    //При некорректном названии параметра или типе данных в запросе выбрасывает UnknownException
    @Auditable
    @DeleteMapping
    public void deleteTaskInParam(String id) {
        if(NumberUtils.isCreatable(id))
            taskService.deleteTask(Long.parseLong(id));
        else
            throw new UnknownException("There is no 'id' parameter in request body or it is not valid");
    }

    //Метод, выполняющий проверку корректности введенного ID(проверяет, является ли числом) в адресе запроса и посылающий запрос в сервис на удаление задачи
    //При некорректном типе данных в адресе запроса выбрасывает UnknownException
    @Auditable
    @DeleteMapping(value = "{id}")
    public void deleteTask(@PathVariable(value = "id") String id) {
        if(NumberUtils.isCreatable(id))
            taskService.deleteTask(Long.parseLong(id));
        else
            throw new UnknownException("Requested id is not valid");
    }
}
