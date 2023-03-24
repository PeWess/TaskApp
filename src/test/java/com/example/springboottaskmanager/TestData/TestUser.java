package com.example.springboottaskmanager.TestData;

import com.example.springboottaskmanager.model.Role;
import com.example.springboottaskmanager.model.RoleName;
import com.example.springboottaskmanager.model.User;

public class TestUser {
    private static Role testRole = new Role(1L, RoleName.ADMIN);
    public static User testUser = new User(1L, "test", "test", "test", "test", testRole, true);
}
