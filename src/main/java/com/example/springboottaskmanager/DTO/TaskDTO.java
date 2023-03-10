package com.example.springboottaskmanager.DTO;

import com.example.springboottaskmanager.customValidator.StatusValid;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Модель данных для тестирования функций Mapper
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "^[A-Z А-Я].*", message = "Название задачи должно начинаться с заглавной буквы")
    private String taskName;
    private String taskType;
    @StatusValid(message = "Задача может быть 'Completed', 'In completion', 'Failed'")
    private String status;
    private String executor;
    @NotBlank(message = "У задачи должен быть владелец")
    private String ownBy;
}
