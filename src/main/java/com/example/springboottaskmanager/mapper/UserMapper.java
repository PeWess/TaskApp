package com.example.springboottaskmanager.mapper;

import com.example.springboottaskmanager.model.User;
import com.example.springboottaskmanager.security.securityModels.RegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    default User toUser(RegisterRequest request, ToUserMapper toUserMapper, Long roleId) {
        User user = new User();
        user.setUsername(request.getUsername());
        toUserMapper.EncryptPassword(user, request.getPassword());
        user.setFirstname(request.getFirstname());
        user.setSurname(request.getSurname());
        toUserMapper.AddRole(user, roleId);
        user.setEnabled(true);
        return user;
    }
}
