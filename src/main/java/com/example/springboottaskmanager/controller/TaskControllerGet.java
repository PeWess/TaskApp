package com.example.springboottaskmanager.controller;

import com.example.springboottaskmanager.aspect.Auditable;
import com.example.springboottaskmanager.DTO.TaskDTO;
import com.example.springboottaskmanager.exception.Body.UnknownException;
import com.example.springboottaskmanager.model.Task;
import com.example.springboottaskmanager.service.TaskServiceGet;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks/")
public class TaskControllerGet {
    @Autowired
    TaskServiceGet taskService;

    //Метод, выполняющий проверку корректности введенного ID(проверяет, является ли числом) в параметрах запроса и посылающий запрос в сервис на получение задачи
    //При некорректном названии параметра или типе данных в запросе выбрасывает UnknownException
    @Auditable
    @GetMapping
    public TaskDTO getTaskInParam(String id) {
        if(NumberUtils.isCreatable(id))
            return taskService.getTaskById(Long.parseLong(id));
        else
            throw new UnknownException("There is no 'id' parameter in request body or it is not valid");
    }

    //Метод, выполняющий проверку корректности введенного ID(проверяет, является ли числом) в параметрах запроса и посылающий запрос в сервис на получение задачи
    //При некорректном типе данных в адресе запроса выбрасывает UnknownException
    @Auditable
    @GetMapping(value = "{id}")
    public TaskDTO getTask(@PathVariable(value = "id") String id) {
        if(NumberUtils.isCreatable(id))
            return taskService.getTaskById(Long.parseLong(id));
        else
            throw new UnknownException("Requested id is not valid");
    }

    //Метод, посылающий запрос в сервис на возвращение всех объектов в базе данных
    @Auditable
    @GetMapping("all")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }
}
