package com.example.department_app.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "user_and_department")
data class UserAndDepartment (
    @Id
    @Column(name = "userId")
    @NotNull
    private var userId: Long,
    @Column(name = "departmentId")
    @NotNull
    private var departmentId: Long
){
    constructor() : this(0L, 0L)
}