package com.example.springboottaskmanager.controller;

import com.example.springboottaskmanager.aspect.Auditable;
import com.example.springboottaskmanager.DTO.TaskDTO;
import com.example.springboottaskmanager.mapper.TaskMapper;
import com.example.springboottaskmanager.model.Task;
import com.example.springboottaskmanager.service.TaskServicePost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks/")
public class TaskControllerPost {
    @Autowired
    TaskServicePost taskService;

    @Auditable
    @PostMapping
    public Task createTask(@RequestBody TaskDTO task) {
        Task taskModel = TaskMapper.INSTANCE.toModel(task);
        return taskService.createTask(taskModel);
    }
}