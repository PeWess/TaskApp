package com.example.springboottaskmanager.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Модель данных аудита.
 * {@param operation} - метод запроса.
 * {@param status} - HTTP статус.
 * {@param error_message} - собщение об ошибках, если они были вызваны.
 */
@Entity
@Table(name = "audit")
@Setter
@Getter
@NoArgsConstructor
public class Audit {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "method_name")
    private String methodName;

    @NotNull
    @Column(name = "operation")
    private String operation;

    @NotNull
    @Column(name = "status")
    private String status;

    @Nullable
    @Column(name = "error_message")
    private String errorMessage;
}