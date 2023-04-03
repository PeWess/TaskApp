package com.example.department_app.repo

import com.example.department_app.model.Department
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface DepartmentRepo : JpaRepository<Department, Long> {
    @Query(value = "SELECT d.department_name FROM department d INNER JOIN user_and_department u ON d.id = u.department_id " +
            "WHERE u.user_id = ?1", nativeQuery = true)
    fun getUserDepartment(userId: Long): Optional<String>
}