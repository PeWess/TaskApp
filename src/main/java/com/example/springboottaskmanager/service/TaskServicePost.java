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
    /**
     * Метод, сохраняющий валидную задачу.
     * @param task - сохраняемая задача.
     * @param taskRepo - база задач.
     * @return - сохраненную задачу.
     */
    public Task CreateTask(Task task, TaskRepo taskRepo) {
        taskRepo.save(task);
        return task;
    }
}