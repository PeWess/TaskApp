package com.example.springboottaskmanager.security.atoken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {
    @Query(value = "SELECT t FROM Token t INNER JOIN User u ON t.user.id = u.id " +
            "WHERE u.id = :id AND(t.expired = false OR t.revoked = false)")
    List<Token> findValidUserToken(Long id);
    Optional<Token> findByToken(String token);
}
