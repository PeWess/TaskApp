package com.example.springboottaskmanager.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "task_name")
    @NotNull
    private String taskName;
    @Column(name = "task_type")
    private String taskType;
    @Column(name = "status")
    private String status;
    @ManyToOne
    @JoinColumn(name = "executor_id")
    private Worker executor;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    @NotNull
    private Worker ownBy;
}
