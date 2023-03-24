package com.example.springboottaskmanager.service;

import com.example.springboottaskmanager.exception.Body.NotFoundException;
import com.example.springboottaskmanager.repository.TaskRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskServiceDelete {
    /**
     * Метод, удаляющий запись из базы данных.
     * @param id - идентификатор удаляемой задачи.
     * @param taskRepo - база задач.
     * @throws NotFoundException - если записи с указанным id не существует, выбрасывает исключение.
     */
    @Transactional
    public void DeleteTask(Long id, TaskRepo taskRepo) throws NotFoundException {
        if (taskRepo.findById(id).isPresent())
            taskRepo.deleteById(id);
        else
            throw new NotFoundException("Задача не найдена");
    }
}