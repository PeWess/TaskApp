package com.example.department_app.controller

import com.example.department_app.exception.UnknownException
import com.example.department_app.service.DepartmentService
import org.apache.commons.lang3.math.NumberUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/departments")
class DepartmentController{
    @Autowired
    private lateinit var departmentService: DepartmentService

    var errorMessage: String = "Requested id is not valid"

    @PostMapping("/post/{departmentId}/{userId}")
    fun postUserDepartment(@PathVariable departmentId: String, @PathVariable userId: String): String {
        if(NumberUtils.isCreatable(userId) && NumberUtils.isCreatable(departmentId))
            return departmentService.postUserDepartment(userId.toLong(), departmentId.toLong())
        else
            throw UnknownException(errorMessage)
    }

    @GetMapping("/get/{userId}")
    fun getDepartment(@PathVariable userId: String): String {
        if(NumberUtils.isCreatable(userId))
            return departmentService.getDepartment(userId.toLong())
        else
            throw UnknownException(errorMessage)
    }

    @DeleteMapping("/delete/{userId}")
    fun deleteUser(@PathVariable userId: String): String {
        if(NumberUtils.isCreatable(userId))
            return departmentService.deleteUser(userId.toLong())
        else
            throw UnknownException(errorMessage)
    }
}