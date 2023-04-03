package com.example.springboottaskmanager.service.departmentservices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DepartmentServicePost {
    /**
     * Интерфейс для Feign клиента.
     */
    public final DepartmentsFeignClient departmentsFeignClient;

    /**
     * Обращение через RestTemplate-сервис для добалвения пользователя к базе данных подразделений.
     * @param userId Идентификатор пользователя.
     * @param depId Идентификатор подразделения.
     * @return Сообщение о добавлении пользователя.
     */
    @Transactional
    public String postUserDepartmentRestTemplate(final String userId, final String depId) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://localhost:5433/departments/post/" + depId + "/" + userId;
        return restTemplate.postForObject(uri, "", String.class);
    }

    /**
     * Обращение через Feign-сервис для добалвения пользователя к базе данных подразделений.
     * @param userId Идентификатор пользователя.
     * @param depId Идентификатор подразделения.
     * @return Сообщение о добавлении пользователя.
     */
    @Transactional
    public String postUserDepartmentFeign(final String userId, final String depId) {
        return departmentsFeignClient.postUserDepartmentFeign(Long.parseLong(depId), Long.parseLong(userId));
    }

    /**
     * Обращение через WebClient-сервис для добалвения пользователя к базе данных подразделений.
     * @param userId Идентификатор пользователя.
     * @param depId Идентификатор подразделения.
     * @return Сообщение о добавлении пользователя.
     */
    @Transactional
    public String postUserDepartmentWebClient(final String userId, final String depId) {
        WebClient webClient = WebClient.create("http://localhost:5433");

        Mono<String> result = webClient.post()
                .uri("/departments/post/" + depId + "/" + userId)
                .retrieve()
                .bodyToMono(String.class);

        return result.block();
    }
}
