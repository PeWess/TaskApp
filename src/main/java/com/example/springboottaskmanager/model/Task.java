package com.example.springboottaskmanager.model;

import jakarta.persistence.*;
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
    @Column(name = "task_name")
    private String taskName;
    @Column(name = "task_type")
    private String taskType;
    @Column(name = "status")
    private String status;
    @Column(name = "executor")
    private String executor;
    @Column(name = "own_by")
    private String ownBy;

    @Override
    public String toString() {
        return "{\"id\":" + id + ",\"taskName\":\"" + taskName + "\",\"taskType\":\"" + taskType + "\",\"status\":\"" + status + "\",\"executor\":\"" + executor + "\",\"ownBy\":\"" + ownBy + "\"}";
    }
}
