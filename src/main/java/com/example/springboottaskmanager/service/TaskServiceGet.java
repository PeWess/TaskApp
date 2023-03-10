package com.example.springboottaskmanager.service;

import com.example.springboottaskmanager.DTO.TaskDTO;
import com.example.springboottaskmanager.exception.Body.NotFoundException;
import com.example.springboottaskmanager.mapper.TaskMapper;
import com.example.springboottaskmanager.model.Task;
import com.example.springboottaskmanager.repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceGet {
    @Autowired
    public TaskRepo taskRepo;

    //Метод, возвращающий запись из базы данных, если запись с указанным ID существует, иначе выбрасывает NotFoundException
    public TaskDTO getTaskById(Long id) throws NotFoundException {
        if(taskRepo.findById(id).isPresent()) {
            return TaskMapper.INSTANCE.toDTO(taskRepo.findById(id).get());
        }
        else
            throw new NotFoundException("Задача не найдена");
    }

    //Метод, возвращающий список со всеми записями в базе данных
    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }
}
