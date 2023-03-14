package com.example.springboottaskmanager.DTO;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    @Id
    private Long id;
    @Pattern(regexp = "^[A-Z А-Я].*", message = "Название задачи должно начинаться с заглавной буквы")
    private String task_name;
    private String task_type;
    /*@StatusValid(message = "Задача может быть 'Completed', 'In completion', 'Failed'")*/
    private String status;
    private String executor;
    @NotBlank(message = "У задачи должен быть владелец")
    private String own_by;
}
