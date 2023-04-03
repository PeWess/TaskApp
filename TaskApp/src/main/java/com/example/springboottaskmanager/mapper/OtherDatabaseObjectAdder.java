package com.example.springboottaskmanager.mapper;

import com.example.springboottaskmanager.viewmodel.TaskViewModel;
import com.example.springboottaskmanager.model.Task;
import com.example.springboottaskmanager.repository.WorkerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Класс используется для добавления информации об исполнителе и владельце задачи.
 */
@Component
@RequiredArgsConstructor
public class OtherDatabaseObjectAdder {
    /**
     * Репозиторий работников.
     */
    private final WorkerRepo workerRepo;

    /**
     * Метод добавляет исполнителя(если такой есть) и владельца,
     * полученных из view задачи, к модели задачи.
     * @param taskViewModel View задачи.
     * @param task Модель задачи.
     */
    public void addTaskWorkers(final TaskViewModel taskViewModel, final Task task) {
        if (taskViewModel.getExecutorId() != null) {
            workerRepo.findById(taskViewModel.getExecutorId())
                    .ifPresent(task::setExecutor);
        } else {
            task.setExecutor(null);
        }

        workerRepo.findById(taskViewModel.getOwnerId())
                .ifPresent(task::setOwnBy);
    }
}
