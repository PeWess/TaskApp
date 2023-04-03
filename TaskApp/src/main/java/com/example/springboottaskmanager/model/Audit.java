package com.example.springboottaskmanager.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Модель данных аудита.
 * {@param method_name} - вызываемый метод в программе.
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
    /**
     * Идентификатор записи.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Метод, выбросивший исключение.
     */
    @NotNull
    @Column(name = "method_name")
    private String methodName;

    /**
     * Тип HTTP-запроса.
     */
    @NotNull
    @Column(name = "operation")
    private String operation;

    /**
     * HTTP-статус.
     */
    @NotNull
    @Column(name = "status")
    private String status;

    /**
     * Сообщение о ошибке, если она есть.
     */
    @Nullable
    @Column(name = "error_message")
    private String errorMessage;
}
