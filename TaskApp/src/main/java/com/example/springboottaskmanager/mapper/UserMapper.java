package com.example.springboottaskmanager.mapper;

import com.example.springboottaskmanager.model.User;
import com.example.springboottaskmanager.security.securitymodels.RegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Метод, передающий информацию из формы регистрации в модель данных.
     * @param request Тело запроса на регистрацию.
     * @param toUserMapper Класс, добавляющий более сложную
     * или связанную с другими таблицам информацию.
     * @param roleId Идентификатор роли.
     * @return Модель данныхпользователя.
     */
    default User toUser(
            RegisterRequest request, ToUserMapper toUserMapper, Long roleId) {
        User user = new User();
        user.setUsername(request.getUsername());
        toUserMapper.encryptPassword(user, request.getPassword());
        user.setFirstname(request.getFirstname());
        user.setSurname(request.getSurname());
        toUserMapper.addRole(user, roleId);
        toUserMapper.setDepartmentId(user);
        user.setEnabled(true);
        return user;
    }
}
