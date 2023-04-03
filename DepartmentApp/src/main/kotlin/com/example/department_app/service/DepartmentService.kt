package com.example.department_app.service

import com.example.department_app.exception.NotFoundException
import com.example.department_app.model.UserAndDepartment
import com.example.department_app.repo.DepartmentRepo
import com.example.department_app.repo.UserAndDepartmentRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DepartmentService{
    @Autowired
    private final lateinit var departmentRepo: DepartmentRepo
    @Autowired
    private final lateinit var userAndDepartmentRepo: UserAndDepartmentRepo

    @Transactional
    fun postUserDepartment(userId: Long, departmentId: Long): String {
        userAndDepartmentRepo.save(UserAndDepartment(userId, departmentId))
        return "Added user to department"
    }

    @Transactional
    fun getDepartment(userId: Long): String{
        if (departmentRepo.getUserDepartment(userId).isPresent)
            return departmentRepo.getUserDepartment(userId).get()
        else
            throw NotFoundException("Такого пользователя нет в базе данных")
    }

    @Transactional
    fun deleteUser(userId: Long): String{
        if (departmentRepo.getUserDepartment(userId).isPresent) {
            userAndDepartmentRepo.deleteById(userId)
            return "Запись о пользователе удалена"
        }
        else
            throw NotFoundException("Такого пользователя нет в базе данных")
    }
}