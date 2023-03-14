package com.example.springboottaskmanager.controller;

import com.example.springboottaskmanager.DTO.TaskDTO;
import com.example.springboottaskmanager.aspect.Auditable;
import com.example.springboottaskmanager.exception.Body.UnknownException;
import com.example.springboottaskmanager.model.Task;
import com.example.springboottaskmanager.service.TaskServiceFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Контроллер, передающий валидные запросы в фасад сервиса {@link TaskServiceFacade}
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    final TaskServiceFacade taskService;

    @Auditable
    @PostMapping
    public Task CreateTask(@Valid @RequestBody TaskDTO task) {
        return taskService.CreateTask(task);
    }

    @Auditable
    @GetMapping("{id}")
    public TaskDTO GetTask(@PathVariable String id) {
        if(NumberUtils.isCreatable(id))
            return taskService.GetTask(Long.parseLong(id));
        else
            throw new UnknownException("Requested id is not valid");
    }

    @Auditable
    @GetMapping("all")
    public List<TaskDTO> GetAllTasks() {
        return taskService.GetAllTasks();
    }

    @Auditable
    @DeleteMapping("{id}")
    public String DeleteTask(@PathVariable String id) {
        if(NumberUtils.isCreatable(id))
            return taskService.DeleteTask(Long.parseLong(id));
        else
            throw new UnknownException("Requested id is not valid");
    }
}
