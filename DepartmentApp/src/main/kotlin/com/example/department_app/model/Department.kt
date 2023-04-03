package com.example.department_app.model

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "department")
data class Department(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id : Long,
    @Column(name = "department_name")
    @NotNull
    private var departmentName : String) {
    constructor() : this(0L, "dummy dep")
}