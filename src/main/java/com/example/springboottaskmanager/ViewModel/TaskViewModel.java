package com.example.springboottaskmanager.ViewModel;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class TaskViewModel {
    @Id
    private Long id;
    @Pattern(regexp = "^[A-Z А-Я].*", message = "Название задачи должно начинаться с заглавной буквы")
    @NotNull
    private String task_name;
    private String task_type;
    private String status;
    private Long executor_id;
    @NotNull
    private Long owner_id;

    @Override
    public String toString() {
        return "{\"id\":" + id + ",\"task_name\":\"" + task_name + "\",\"task_type\":\"" + task_type + "\",\"status\":\"" + status + "\",\"executor_id\":" + executor_id + ",\"owner_id\":" + owner_id + "}";
    }
}
