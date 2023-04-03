package com.example.springboottaskmanager.repository;

import com.example.springboottaskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    /**
     * Метод находит пользователя по его логину.
     * @param username Логин пользователя.
     * @return Пользователя с заданным логином.
     */
    Optional<User> findByUsername(String username);
}
