package com.example.springboottaskmanager.service.departmentservices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DepartmentServiceGet {
    /**
     * Интерфейс для Feign клиента.
     */
    private final DepartmentsFeignClient departmentsFeignClient;

    /**
     * Обращение через RestTemplate-сервис для получения названия подразделения пользователя.
     * @param id Идентификатор пользователя.
     * @return Название департамента пользователя.
     */
    @Transactional
    public String getUserDepartmentRestTemplate(final String id) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://localhost:5433/departments/get/" + id;
        return restTemplate.getForObject(uri, String.class);
    }

    /**
     * Обращение через Feign-сервис для получения названия подразделения пользователя.
     * @param id Идентификатор пользователя.
     * @return Название департамента пользователя.
     */
    @Transactional
    public String getUserDepartmentFeign(final String id) {
        return departmentsFeignClient.getUserDepartmentFeign(Long.parseLong(id));
    }

    /**
     * Обращение через WebClient-сервис для получения названия подразделения пользователя.
     * @param id Идентификатор пользователя.
     * @return Название департамента пользователя.
     */
    @Transactional
    public String getUserDepartmentWebClient(final String id) {
        WebClient webClient = WebClient.create("http://localhost:5433");

        Mono<String> result = webClient.get()
                .uri("/departments/get/" + id)
                .retrieve()
                .bodyToMono(String.class);

        return result.block();
    }
}
