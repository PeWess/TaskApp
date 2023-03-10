package com.example.springboottaskmanager.service;

import com.example.springboottaskmanager.model.Task;
import com.example.springboottaskmanager.repository.TaskRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class TaskServicePost {
    @Autowired
    TaskRepo taskRepo;

    //Метод, сохраняющий валидную запись, иначе выбрасывает ConstraintViolationException
    public Task createTask(@Valid Task task) {
        taskRepo.save(task);
        return task;
    }
}