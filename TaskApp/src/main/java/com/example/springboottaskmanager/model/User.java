package com.example.springboottaskmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    /**
     * Идентификатор пользователя.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Логин пользователя.
     */
    @Column(name = "username")
    @NotNull
    private String username;
    /**
     * Пароль пользователя.
     */
    @Column(name = "password")
    @NotNull
    private String password;
    /**
     * Имя пользователя.
     */
    @Column(name = "firstname")
    @NotNull
    private String firstname;
    /**
     * Фамилия пользователя.
     */
    @Column(name = "surname")
    @NotNull
    private String surname;
    /**
     * Роль пользователя.
     */
    @Enumerated(EnumType.STRING)
    @ManyToOne
    @JoinColumn(name = "role")
    private Role role;
    /**
     * Идентификатор департамента пользователя.
     */
    @Column(name = "department_id")
    @NotNull
    private Long departmentId;
    /**
     * Переменная, отвечающая, активен ли профиль пользователя.
     */
    @Column(name = "enabled")
    private boolean enabled;

    /**
     * Метод, возвращающий список ролей пользователя.
     * @return Роли пользователя.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(role.getName().toString()));
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
