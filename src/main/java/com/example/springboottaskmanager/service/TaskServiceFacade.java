package com.example.springboottaskmanager.service;

import com.example.springboottaskmanager.DTO.TaskDTO;
import com.example.springboottaskmanager.mapper.TaskMapper;
import com.example.springboottaskmanager.model.Task;
import com.example.springboottaskmanager.repository.TaskRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, направляющий запросы из контроллера в вызываемые сервисы
 */
@Component
@RequiredArgsConstructor
public class TaskServiceFacade {
    final private TaskRepo taskRepo;
    final private TaskServicePost postService;
    final private TaskServiceGet getService;
    final private TaskServiceDelete deleteService;

    public Task CreateTask(TaskDTO task) {
        Task taskModel = TaskMapper.INSTANCE.toModel(task);
        return postService.CreateTask(taskModel, taskRepo);
    }

    public TaskDTO GetTask(Long id) {
        Task taskModel = getService.GetTaskById(id, taskRepo);
        return TaskMapper.INSTANCE.toDTO(taskModel);
    }

    public List<TaskDTO> GetAllTasks() {
        List<Task> taskModels = getService.GetAllTasks(taskRepo);
        List<TaskDTO> taskDTOs = new ArrayList<>();

        for (Task taskModel : taskModels) {
            taskDTOs.add(TaskMapper.INSTANCE.toDTO(taskModel));
        }

        return taskDTOs;
    }

    public String DeleteTask(Long id) {
        deleteService.DeleteTask(id, taskRepo);
        return "Task " + id + " successfully deleted";
    }
}
