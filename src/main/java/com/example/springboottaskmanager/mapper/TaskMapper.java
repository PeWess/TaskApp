package com.example.springboottaskmanager.mapper;


import com.example.springboottaskmanager.ViewModel.TaskViewModel;
import com.example.springboottaskmanager.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(source = "taskName", target = "task_name")
    @Mapping(source = "taskType", target = "task_type")
    @Mapping(source = "executor.id", target = "executor_id")
    @Mapping(source = "ownBy.id", target = "owner_id")
    TaskViewModel toViewModel(Task task);

    default Task toModel(TaskViewModel taskViewModel, OtherDatabaseObjectAdder otherDatabaseObjectAdder) {
        Task result = new Task();
        result.setId(taskViewModel.getId());
        result.setTaskName(taskViewModel.getTask_name());
        result.setTaskType(taskViewModel.getTask_type());
        result.setStatus(taskViewModel.getStatus());
        otherDatabaseObjectAdder.AddTaskWorkers(taskViewModel, result);
        return result;
    }
}