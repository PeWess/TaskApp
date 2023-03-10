/*
package com.example.springboottaskmanager.controller;

import com.example.springboottaskmanager.DTO.TaskDTO;
import com.example.springboottaskmanager.model.Task;
import com.example.springboottaskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks/")
public class TaskController {
    @Autowired
    TaskService taskService;

    @PostMapping
    public Task createTask(@RequestBody TaskDTO task) {
        return taskService.createTask(task);
    }

    @GetMapping
    public TaskDTO getTask(@RequestParam("id") String id) {
        return taskService.getTaskById(Long.parseLong(id));
    }

    @GetMapping("all")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @DeleteMapping
    public void deleteTask(@RequestParam("id") String id) {
        taskService.deleteTask(Long.parseLong(id));
    }
}
*/
