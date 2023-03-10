package com.example.springboottaskmanager.model;

import com.example.springboottaskmanager.customValidator.StatusValid;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Pattern(regexp = "^[A-Z А-Я].*", message = "Название задачи должно начинаться с заглавной буквы")
    @Column(name = "task_name")
    private String taskName;
    @Column(name = "task_type")
    private String taskType;
    @StatusValid(message = "Задача может быть 'Completed', 'In completion', 'Failed'")
    @Column(name = "status")
    private String status;
    @Column(name = "executor")
    private String executor;
    @NotBlank(message = "У задачи должен быть владелец")
    @Column(name = "own_by")
    private String ownBy;

    @Override
    public String toString() {
        return "{\"id\":" + id + ",\"taskName\":\"" + taskName + "\",\"taskType\":\"" + taskType + "\",\"status\":\"" + status + "\",\"executor\":\"" + executor + "\",\"ownBy\":\"" + ownBy + "\"}";
    }
}
