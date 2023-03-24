package com.example.springboottaskmanager.mapper;

import com.example.springboottaskmanager.ViewModel.TaskViewModel;
import com.example.springboottaskmanager.model.Task;
import com.example.springboottaskmanager.repository.WorkerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OtherDatabaseObjectAdder {
    private final WorkerRepo workerRepo;

    public void AddTaskWorkers(TaskViewModel taskViewModel, Task task) {
        if(taskViewModel.getExecutor_id() != null)
            task.setExecutor(workerRepo.findById(taskViewModel.getExecutor_id()).get());
        else
            task.setExecutor(null);
        task.setOwnBy(workerRepo.findById(taskViewModel.getOwner_id()).get());
    }
}
