package com.example.department_app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DepartmentAppApplication

fun main(args: Array<String>) {
	runApplication<DepartmentAppApplication>(*args)
}