package com.example.springboottaskmanager.mapper;


import com.example.springboottaskmanager.viewmodel.TaskViewModel;
import com.example.springboottaskmanager.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    /**
     * Метод, передающий информацию из модели данных задачи в ее view.
     * @param task Модель задачи.
     * @return view задачи
     */
    @Mapping(source = "executor.id", target = "executorId")
    @Mapping(source = "ownBy.id", target = "ownerId")
    TaskViewModel toViewModel(Task task);

    /**
     * Метод, передающий информацию из view задачи в модель данных.
     * @param taskViewModel View задачи.
     * @param otherDatabaseObjectAdder Класс, добавляющий к задаче информацию
     * из разных таблиц {@link OtherDatabaseObjectAdder}.
     * @return Результат преобразования view в модель.
     */
    default Task toModel(
            TaskViewModel taskViewModel,
            OtherDatabaseObjectAdder otherDatabaseObjectAdder) {
        Task result = new Task();
        result.setId(taskViewModel.getId());
        result.setTaskName(taskViewModel.getTaskName());
        result.setTaskType(taskViewModel.getTaskType());
        result.setStatus(taskViewModel.getStatus());
        otherDatabaseObjectAdder.addTaskWorkers(taskViewModel, result);
        return result;
    }
}
