package com.example.springboottaskmanager.mapper;


import com.example.springboottaskmanager.DTO.TaskDTO;
import com.example.springboottaskmanager.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskDTO toDTO(Task task);

    Task toModel(TaskDTO taskDTO);
}
