package com.example.springboottaskmanager.mapper;


import com.example.springboottaskmanager.DTO.TaskDTO;
import com.example.springboottaskmanager.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(source = "taskName", target = "task_name")
    @Mapping(source = "taskType", target = "task_type")
    @Mapping(source = "ownBy", target = "own_by")
    TaskDTO toDTO(Task task);

    @Mapping(source = "task_name", target = "taskName")
    @Mapping(source = "task_type", target = "taskType")
    @Mapping(source = "own_by", target = "ownBy")
    Task toModel(TaskDTO taskDTO);
}
