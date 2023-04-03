package com.example.springboottaskmanager.service.departmentservices;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "userDepartmentFeign", url = "http://localhost:5433")
public interface DepartmentsFeignClient {
    @PostMapping(value = "/departments/post/{departmentId}/{userId}", produces = "application/json")
    String postUserDepartmentFeign(@PathVariable Long departmentId, @PathVariable Long userId);

    @GetMapping(value = "/departments/get/{userId}", produces = "application/json")
    String getUserDepartmentFeign(@PathVariable Long userId);

    @DeleteMapping(value = "/departments/delete/{userId}", produces = "application/json")
    String deleteUserDepartmentFeign(@PathVariable Long userId);
}
