package com.example.springboottaskmanager.security.atoken;

import com.example.springboottaskmanager.TokenType;
import com.example.springboottaskmanager.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "token")
public class Token {
    /**
     * Идентификатор токена.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Соержимое токена.
     */
    @Column(name = "token", unique = true)
    private String tokenCode;
    /**
     * Идентификатор владльца токена.
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    /**
     * Тип токена.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "token_type")
    private TokenType tokenType = TokenType.BEARER;
    /**
     * Переменная указывает, аннклирован ли токен.
     */
    @Column(name = "revoked")
    private boolean revoked;
    /**
     * Переменная указывает, истек ли срок действия токена.
     */
    @Column(name = "expired")
    private boolean expired;
}
