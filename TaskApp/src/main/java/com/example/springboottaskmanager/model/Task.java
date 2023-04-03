package com.example.springboottaskmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
public class Task {
    /**
     * Идентификатор задачи.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Название задачи.
     */
    @Column(name = "task_name")
    @NotNull
    private String taskName;

    /**
     * Тип задачи.
     */
    @Column(name = "task_type")
    private String taskType;

    /**
     * Статус задачи.
     */
    @Column(name = "status")
    private String status;

    /**
     * Идентификатор исполнителя.
     */
    @ManyToOne
    @JoinColumn(name = "executor_id")
    private Worker executor;

    /**
     * Идентификатор владельца.
     */
    @ManyToOne
    @JoinColumn(name = "owner_id")
    @NotNull
    private Worker ownBy;
}
