/*
package com.example.springboottaskmanager.service;

import com.example.springboottaskmanager.DTO.TaskDTO;
import com.example.springboottaskmanager.exception.Body.NotFoundException;
import com.example.springboottaskmanager.exception.Body.UnknownException;
import com.example.springboottaskmanager.mapper.TaskMapperImpl;
import com.example.springboottaskmanager.model.Audit;
import com.example.springboottaskmanager.model.Task;
import com.example.springboottaskmanager.repository.AuditRepo;
import com.example.springboottaskmanager.repository.TaskRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class TaskServiceImpl implements TaskService{
    @Autowired
    TaskRepo taskRepo;
    @Autowired
    AuditRepo auditRepo;

    @Override
    public Task createTask(@Valid TaskDTO task) {
        Audit audit = new Audit();
        audit.setTask_id(task.getId());
        audit.setOperation("POST");

        try {
            Task taskModel = TaskMapperImpl.INSTANCE.toModel(task);
            audit.setResult("Success");
            auditRepo.save(audit);
            return taskRepo.save(taskModel);
        }
        catch (Exception e) {
            audit.setResult("Failed");
            auditRepo.save(audit);
            throw new UnknownException("Произошла непредвиденная ошибка");
        }
    }

    @Override
    public TaskDTO getTaskById(Long id) throws NotFoundException{
        Audit audit = new Audit();
        audit.setTask_id(id);
        audit.setOperation("GET");

        if(taskRepo.findById(id).isPresent()) {
            TaskDTO taskDTO = TaskMapperImpl.INSTANCE.toDTO(taskRepo.findById(id).get());
            audit.setResult("Success");
            auditRepo.save(audit);
            return taskDTO;
        }
        else {
            audit.setResult("Failed");
            auditRepo.save(audit);
            throw new NotFoundException("Задача не найдена");
        }
    }

    @Override
    public List<Task> getAllTasks() {
        Audit audit = new Audit();
        audit.setTask_id(null);
        audit.setOperation("GET_ALL");

        try {
            audit.setResult("Success");
            auditRepo.save(audit);
            return taskRepo.findAll();
        }
        catch (Exception e) {
            audit.setResult("Failed");
            auditRepo.save(audit);
            throw new UnknownException("Произошла непредвиденная ошибка");
        }
    }

    @Override
    public void deleteTask(Long id) throws NotFoundException {
        Audit audit = new Audit();
        audit.setTask_id(id);
        audit.setOperation("DELETE");

        if (taskRepo.findById(id).isPresent()) {
            audit.setResult("Success");
        auditRepo.save(audit);
        taskRepo.deleteById(id);
        }
        else {
            audit.setResult("Failed");
            auditRepo.save(audit);
            throw new NotFoundException("Задача не найдена");
        }
    }
}
*/
