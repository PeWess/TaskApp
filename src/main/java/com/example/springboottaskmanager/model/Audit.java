package com.example.springboottaskmanager.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "audit")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Audit {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*@Nullable
    private String requestBody;*/

    //Запись с методом запроса
    @NotNull
    @Column(name = "operation")
    private String operation;

    //HTTP статус ответа
    @NotNull
    @Column(name = "status")
    private String status;

    //Сообщение об ошибке, если она есть
    @Nullable
    @Column(name = "error_message")
    private String errorMessage;
}