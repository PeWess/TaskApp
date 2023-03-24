package com.example.springboottaskmanager.controller;

import com.example.springboottaskmanager.ViewModel.TaskViewModel;
import com.example.springboottaskmanager.aspect.Auditable;
import com.example.springboottaskmanager.exception.Body.UnknownException;
import com.example.springboottaskmanager.security.securityModels.LoginRequest;
import com.example.springboottaskmanager.security.securityModels.LoginResponse;
import com.example.springboottaskmanager.service.TaskServiceFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
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
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> Login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(taskService.Login(loginRequest));
    }

    @Auditable
    @PostMapping("/post")
    public ResponseEntity<TaskViewModel> CreateTask(@Valid @RequestBody TaskViewModel task) {
        return taskService.CreateTask(task);
    }

    @Auditable
    @GetMapping("/get/{id}")
    public TaskViewModel GetTask(@PathVariable String id) {
        if(NumberUtils.isCreatable(id))
            return taskService.GetTask(Long.parseLong(id));
        else
            throw new UnknownException("Requested id is not valid");
    }

    @Auditable
    @GetMapping("/get/all")
    public List<TaskViewModel> GetAllTasks() {
        return taskService.GetAllTasks();
    }

    @Auditable
    @DeleteMapping("/delete/{id}")
    public String DeleteTask(@PathVariable String id) {
        if(NumberUtils.isCreatable(id))
            return taskService.DeleteTask(Long.parseLong(id));
        else
            throw new UnknownException("Requested id is not valid");
    }
}
