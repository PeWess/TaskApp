package com.example.springboottaskmanager.service;

import com.example.springboottaskmanager.ViewModel.TaskViewModel;
import com.example.springboottaskmanager.mapper.OtherDatabaseObjectAdder;
import com.example.springboottaskmanager.mapper.TaskMapper;
import com.example.springboottaskmanager.model.Task;
import com.example.springboottaskmanager.repository.TaskRepo;
import com.example.springboottaskmanager.repository.UserRepo;
import com.example.springboottaskmanager.security.securityModels.LoginRequest;
import com.example.springboottaskmanager.security.securityModels.LoginResponse;
import com.example.springboottaskmanager.security.securityModels.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    final private UserRepo userRepo;
    final private OtherDatabaseObjectAdder otherDatabaseObjectAdder;
    final private TaskServicePost postService;
    final private TaskServiceGet getService;
    final private TaskServiceDelete deleteService;
    final private TaskServiceLogin loginService;

    public LoginResponse Register(RegisterRequest registerRequest) {
        return loginService.Register(registerRequest, userRepo);
    }

    public LoginResponse Login(LoginRequest loginRequest) {
        return loginService.Login(loginRequest, userRepo);
    }

    public ResponseEntity<TaskViewModel> CreateTask(TaskViewModel taskViewModel) {
        Task taskModel = TaskMapper.INSTANCE.toModel(taskViewModel, otherDatabaseObjectAdder);

        postService.CreateTask(taskModel, taskRepo);

        return new ResponseEntity<>(TaskMapper.INSTANCE.toViewModel(taskModel), HttpStatus.CREATED);
    }

    public TaskViewModel GetTask(Long id) {
        Task taskModel = getService.GetTaskById(id, taskRepo);
        return TaskMapper.INSTANCE.toViewModel(taskModel);
    }

    public List<TaskViewModel> GetAllTasks() {
        List<Task> taskModels = getService.GetAllTasks(taskRepo);
        List<TaskViewModel> taskViewModels = new ArrayList<>();

        for (Task taskModel : taskModels) {
            taskViewModels.add(TaskMapper.INSTANCE.toViewModel(taskModel));
        }

        return taskViewModels;
    }

    public String DeleteTask(Long id) {
        deleteService.DeleteTask(id, taskRepo);
        return "Task " + id + " successfully deleted";
    }
}
