package com.example.springboottaskmanager.viewmodel;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class TaskViewModel {
    /**
     * Идентификатор задачи.
     */
    @Id
    private Long id;
    /**
     * Название задачи.
     */
    @Pattern(regexp = "^[A-Z А-Я].*", message = "Название задачи должно начинаться с заглавной буквы")
    @NotNull
    private String taskName;
    /**
     * Тип задачи.
     */
    private String taskType;
    /**
     * Статус задачи.
     */
    private String status;
    /**
     * Идентификатор исполнителя задачи.
     */
    private Long executorId;
    /**
     * Идентификатор владельца задачи.
     */
    @NotNull
    private Long ownerId;

    /**
     * @return Строку с задачей.
     */
    @Override
    public String toString() {
        return "{\"id\":" + id
                + ",\"taskName\":\""  + taskName
                + "\",\"taskType\":\"" + taskType
                + "\",\"status\":\"" + status
                + "\",\"executorId\":" + executorId
                + ",\"ownerId\":" + ownerId + "}";
    }
}
