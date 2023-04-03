package com.example.springboottaskmanager.controller;

import com.example.springboottaskmanager.aspect.Auditable;
import com.example.springboottaskmanager.exception.body.NotFoundException;
import com.example.springboottaskmanager.exception.body.UnknownException;
import com.example.springboottaskmanager.service.departmentservices.DepartmentServiceGet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

/**
 * Контроллер, передающий GET запросы другому сервису,
 * работающему с департаментами пользователей.
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/taskApp/departments")
public class DepartmentController {
    /**
     * Переменная окружения, определяющая способ обращения к наружнему сервису.
     */
    @Value("${department_client}")
    private String departmentClient;
    /**
     * Сервис департаментов.
     */
    private final DepartmentServiceGet departmentServiceGet;

    /**
     * Метод вызывает другой метод класса {@link DepartmentServiceGet}
     * для получения департамента пользователя.
     * @param userId Идентификатор пользователя.
     * @return Департамент пользователя.
     */
    @Auditable
    @GetMapping("/get/{userId}")
    public String getUserDepartment(@PathVariable final String userId) {
        try {
            return switch (departmentClient) {
                case "FeignClient" -> departmentServiceGet
                        .getUserDepartmentFeign(userId);
                case "WebClient" -> departmentServiceGet
                        .getUserDepartmentWebClient(userId);
                case "RestTemplate" -> departmentServiceGet
                        .getUserDepartmentRestTemplate(userId);
                default -> "Переменная department_client может быть:"
                       + "RestTemplate, FeignClient или WebClient";
            };
        } catch (HttpClientErrorException httpClientErrorException) {
            throw new NotFoundException(httpClientErrorException.getMessage());
        } catch (ResourceAccessException resourceAccessException) {
            throw new UnknownException("Сервис подразделений недоступен");
        }
    }
}
