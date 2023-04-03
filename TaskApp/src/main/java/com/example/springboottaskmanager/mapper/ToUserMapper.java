package com.example.springboottaskmanager.mapper;

import com.example.springboottaskmanager.model.User;
import com.example.springboottaskmanager.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Класс для добавления в модель {@link User} информации о
 * роли и зашифрованном пароле нового пользователя.
 */
@Component
@RequiredArgsConstructor
public class ToUserMapper {
    /**
     * Репозиторий ролей.
     */
    private final RoleRepo roleRepo;
    /**
     * Кодировщик паролей.
     */
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Добавляет модели пользователя информацию о его роли.
     * @param user Пользователь.
     * @param roleId Идентификатор роли.
     */
    public void addRole(final User user, final Long roleId) {
        roleRepo.findById(roleId).ifPresent(user::setRole);
    }

    /**
     * Добавляет модели пользователя зашифрованный пароль.
     * @param user Пользователь.
     * @param password Пароль до шифрования.
     */
    public void encryptPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
    }

    /**
     * Добавляет модели пользователя информацию о департаменте.
     * @param user Пользователь.
     */
    public void setDepartmentId(final User user) {
        user.setDepartmentId(2L);
    }
}
