package com.example.springboottaskmanager.controller;

import com.example.springboottaskmanager.viewmodel.TaskViewModel;
import com.example.springboottaskmanager.aspect.Auditable;
import com.example.springboottaskmanager.exception.body.UnknownException;
import com.example.springboottaskmanager.security.securitymodels.LoginRequest;
import com.example.springboottaskmanager.security.securitymodels.LoginResponse;
import com.example.springboottaskmanager.security.securitymodels.RegisterRequest;
import com.example.springboottaskmanager.service.taskservices.TaskServiceFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


/**
 * Контроллер, передающий валидные запросы в {@link TaskServiceFacade}.
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/taskApp")
public class TaskController {
    /**
     * Сервис задач.
     */
    private final TaskServiceFacade taskService;

    /**
     * Метод, передающий запрос на регистрацию в {@link TaskServiceFacade}.
     * @param registerRequest Тело запроса на регистрацию.
     * @return JWT-токен.
     */
    @Auditable
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(
            @RequestBody final RegisterRequest registerRequest) {
        return ResponseEntity.ok(taskService.register(registerRequest));
    }

    /**
     * Метод, передающий запрос на авторизацию в {@link TaskServiceFacade}.
     * @param loginRequest Тело запроса на автоизацию.
     * @return JWT-токен.
     */
    @Auditable
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody final LoginRequest loginRequest) {
        return ResponseEntity.ok(taskService.login(loginRequest));
    }

    /**
     * Метод, передающий запрос на сохранение задачи в {@link TaskServiceFacade}.
     * @param task Модель задачи.
     * @return Переданную модель задачи.
     */
    @Auditable
    @PostMapping("/post")
    public ResponseEntity<TaskViewModel> createTask(
            @Valid @RequestBody final TaskViewModel task) {
        return taskService.createTask(task);
    }

    /**
     * Метод, передающий запрос на получение задачи в {@link TaskServiceFacade}.
     * @param id Идентификатор задачи.
     * @return Запрашиваемую с указанным идентификатором.
     */
    @Auditable
    @GetMapping("/get/{id}")
    public TaskViewModel getTask(@PathVariable final String id) {
        if (NumberUtils.isCreatable(id)) {
            return taskService.getTask(Long.parseLong(id));
        } else {
            throw new UnknownException("Requested id is not valid");
        }
    }

    /**
     * Метод, передающий запрос на получение всех задач в {@link TaskServiceFacade}.
     * @return Список всех задач.
     */
    @Auditable
    @GetMapping("/get/all")
    public List<TaskViewModel> getAllTasks() {
        return taskService.getAllTasks();
    }

    /**
     * Метод, передающий запрос на удаление задачи в {@link TaskServiceFacade}.
     * @param id Идентификатор удаляемой задачи.
     * @return Сообщение об удалении задачи.
     */
    @Auditable
    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathVariable final String id) {
        if (NumberUtils.isCreatable(id)) {
            return taskService.deleteTask(Long.parseLong(id));
        } else {
            throw new UnknownException("Requested id is not valid");
        }
    }
}
