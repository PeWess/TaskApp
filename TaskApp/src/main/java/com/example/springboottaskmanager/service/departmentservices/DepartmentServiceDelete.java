package com.example.springboottaskmanager.service.departmentservices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DepartmentServiceDelete {
    /**
     * Интерфейс для Feign клиента.
     */
    private final DepartmentsFeignClient departmentsFeignClient;

    /**
     * Обращение через RestTemplate-сервис для удаления пользователя из базы данных подразделений.
     * @param id Идентификатор пользователя.
     * @return Сообщение об успешном удлаении пользователя.
     */
    @Transactional
    public String deleteUserFromDepartmentRestTemplate(final String id) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://localhost:5433/departments/delete/" + id;
        return restTemplate.getForObject(uri, String.class);
    }

    /**
     * Обращение через Feign-сервис для удаления пользователя из базы данных подразделений.
     * @param id Идентификатор пользователя.
     * @return Сообщение об успешном удлаении пользователя.
     */
    @Transactional
    public String deleteUserFromDepartmentFeign(final String id) {
        return departmentsFeignClient.deleteUserDepartmentFeign(Long.parseLong(id));
    }

    /**
     * Обращение через WebClient-сервис для удаления пользователя из базы данных подразделений.
     * @param id Идентификатор пользователя.
     * @return Сообщение об успешном удлаении пользователя.
     */
    @Transactional
    public String deleteUserFromDepartmentWebClient(final String id) {
        WebClient webClient = WebClient.create("http://localhost:5433");

        Mono<String> result = webClient.get()
                .uri("/departments/delete/" + id)
                .retrieve()
                .bodyToMono(String.class);

        return result.block();
    }
}
