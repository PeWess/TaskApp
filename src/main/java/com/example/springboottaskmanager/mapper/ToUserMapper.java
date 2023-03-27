package com.example.springboottaskmanager.mapper;

import com.example.springboottaskmanager.model.User;
import com.example.springboottaskmanager.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ToUserMapper {
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public void AddRole(User user, Long roleId) {
        user.setRole(roleRepo.findById(roleId).get());
    }

    public void EncryptPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
    }
}
