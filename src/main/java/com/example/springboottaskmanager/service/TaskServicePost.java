package com.example.springboottaskmanager.service;

import com.example.springboottaskmanager.model.Task;
import com.example.springboottaskmanager.repository.TaskRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public Task CreateTask(Task task, TaskRepo taskRepo) {
        taskRepo.save(task);
        return task;
    }
}