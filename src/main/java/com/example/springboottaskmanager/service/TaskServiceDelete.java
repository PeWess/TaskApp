package com.example.springboottaskmanager.service;

import com.example.springboottaskmanager.exception.Body.NotFoundException;
import com.example.springboottaskmanager.repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceDelete {
    @Autowired
    TaskRepo taskRepo;

    //Метод, удаляющий запись в базе данных, если запись с указанным ID существует, иначе выбрасывает NotFoundException
    public void deleteTask(Long id) throws NotFoundException {
        if (taskRepo.findById(id).isPresent())
            taskRepo.deleteById(id);
        else
            throw new NotFoundException("Задача не найдена");
    }
}
