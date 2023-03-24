package com.example.springboottaskmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "worker")
public class Worker {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "worker_name")
    @Pattern(regexp = "^[A-Z А-Я].*", message = "Название задачи должно начинаться с заглавной буквы")
    private String userName;
    @Column(name = "worker_surname")
    @Pattern(regexp = "^[A-Z А-Я].*", message = "Название задачи должно начинаться с заглавной буквы")
    private String userSurname;
    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    List<Task> executor_of_tasks;
    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Task> owner_of_tasks;
}
