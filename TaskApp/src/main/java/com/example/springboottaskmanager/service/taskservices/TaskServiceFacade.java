package com.example.springboottaskmanager.service.taskservices;

import com.example.springboottaskmanager.viewmodel.TaskViewModel;
import com.example.springboottaskmanager.mapper.OtherDatabaseObjectAdder;
import com.example.springboottaskmanager.mapper.TaskMapper;
import com.example.springboottaskmanager.model.Task;
import com.example.springboottaskmanager.repository.TaskRepo;
import com.example.springboottaskmanager.repository.UserRepo;
import com.example.springboottaskmanager.security.securitymodels.LoginRequest;
import com.example.springboottaskmanager.security.securitymodels.LoginResponse;
import com.example.springboottaskmanager.security.securitymodels.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, направляющий запросы из контроллера в вызываемые сервисы.
 */
@Component
@RequiredArgsConstructor
public class TaskServiceFacade {
    /**
     * Репозиторий задач {@link TaskRepo}.
     */
    private final TaskRepo taskRepo;
    /**
     * Репозиторий пользователей {@link UserRepo}.
     */
    private final UserRepo userRepo;
    /**
     * Вспомогательный класс для маппера задач {@link OtherDatabaseObjectAdder}.
     */
    private final OtherDatabaseObjectAdder otherDatabaseObjectAdder;
    /**
     * Сервис, обрабатывающий Post-запросы для сервиса задач {@link TaskServicePost}.
     */
    private final TaskServicePost postService;
    /**
     * Сервис, обрабатывающий Get-запросы для сервиса задач {@link TaskServiceGet}.
     */
    private final TaskServiceGet getService;
    /**
     *Сервис, обрабатывающий Delete-запросы для сервиса задач {@link TaskServiceDelete}.
     */
    private final TaskServiceDelete deleteService;
    /**
     * Сервис, обрабатывающий Get-запросы для авторизации и регистрации пользователей {@link TaskServiceLogin}.
     */
    private final TaskServiceLogin loginService;

    /**
     * Метод для регистрации нового пользователя.
     * @param registerRequest Объект, содержащий информацию о новом пользователе.
     * @return JWT-токен.
     */
    public LoginResponse register(final RegisterRequest registerRequest) {
        return loginService.register(registerRequest, userRepo);
    }

    /**
     * Метод для авторизации пользователя.
     * @param loginRequest Объект, содержащий логин и пароль пользователя.
     * @return JWT-токен.
     */
    public LoginResponse login(final LoginRequest loginRequest) {
        return loginService.login(loginRequest, userRepo);
    }

    /**
     * Метод для сохранения задачи в БД.
     * @param taskViewModel Объект, хранящий данные о здаче в запросе.
     * @return Сохраненную задчу.
     */
    public ResponseEntity<TaskViewModel> createTask(final TaskViewModel taskViewModel) {
        Task taskModel = TaskMapper.INSTANCE.toModel(taskViewModel, otherDatabaseObjectAdder);

        postService.createTask(taskModel, taskRepo);

        return new ResponseEntity<>(TaskMapper.INSTANCE.toViewModel(taskModel), HttpStatus.CREATED);
    }

    /**
     * Метод для получеия задачи по ее id.
     * @param id Идентификатор задачи.
     * @return Запрашиваемую задачу.
     */
    public TaskViewModel getTask(final Long id) {
        Task taskModel = getService.getTaskById(id, taskRepo);
        return TaskMapper.INSTANCE.toViewModel(taskModel);
    }

    /**
     * Метод для получения всех задач из БД.
     * @return Список задач.
     */
    public List<TaskViewModel> getAllTasks() {
        List<Task> taskModels = getService.getAllTasks(taskRepo);
        List<TaskViewModel> taskViewModels = new ArrayList<>();

        for (Task taskModel : taskModels) {
            taskViewModels.add(TaskMapper.INSTANCE.toViewModel(taskModel));
        }

        return taskViewModels;
    }

    /**
     * Метод для удаления здачи.
     * @param id Идентификатор удаляемой задачи.
     * @return Сообщение об успешном уалении задачи.
     */
    public String deleteTask(final Long id) {
        deleteService.deleteTask(id, taskRepo);
        return "Task " + id + " successfully deleted";
    }
}
