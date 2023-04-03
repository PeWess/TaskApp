package com.example.department_app.repo

import com.example.department_app.model.UserAndDepartment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAndDepartmentRepo : JpaRepository<UserAndDepartment, Long> {

}