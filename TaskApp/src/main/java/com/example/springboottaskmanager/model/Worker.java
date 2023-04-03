package com.example.springboottaskmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "worker")
public class Worker {
    /**
     * Идентификатор работника.
     */
    @Id
    @Column(name = "id")
    private Long id;
    /**
     * Имя работника.
     */
    @Column(name = "worker_name")
    @Pattern(regexp = "^[A-Z А-Я].*",
            message = "Имя должно начинаться с заглавной буквы")
    private String userName;
    /**
     * Фамилия работника.
     */
    @Column(name = "worker_surname")
    @Pattern(regexp = "^[A-Z А-Я].*",
            message = "Фамилия должна начинаться с заглавной буквы")
    private String userSurname;
    /**
     * Список задач, исполнителем которых является работник.
     */
    @OneToMany(mappedBy = "id",
            fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Task> executorOfTasks;
    /**
     * Список задач, владельцем которых является работник.
     */
    @OneToMany(mappedBy = "id",
            fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Task> ownerOfTasks;
}
