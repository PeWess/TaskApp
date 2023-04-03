package com.example.springboottaskmanager.service.taskservices;

import com.example.springboottaskmanager.exception.body.NotFoundException;
import com.example.springboottaskmanager.model.Task;
import com.example.springboottaskmanager.repository.TaskRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskServiceGet {
    /**
     * Метод, возвращающий запись из базы данных.
     * @param id - идентификатор задачи.
     * @param taskRepo - база задач.
     * @return {@link Task} - задачу с указанными id.
     * @throws NotFoundException - выбрасывает исключение, если задачи с указанным id не существует.
     */
    @Transactional
    public Task getTaskById(final Long id, final TaskRepo taskRepo) throws NotFoundException {
        if (taskRepo.findById(id).isPresent()) {
            return taskRepo.findById(id).get();
        } else {
            throw new NotFoundException("Задача не найдена");
        }
    }

    /**
     * Метод, возвращающий все записи из базы данных.
     * @param taskRepo - база задач.
     * @return - лист задач.
     */
    @Transactional
    public List<Task> getAllTasks(final TaskRepo taskRepo) {
        return taskRepo.findAll();
    }
}