package com.unez.securityservice.sec.service;

import com.unez.securityservice.sec.entities.AppRole;
import com.unez.securityservice.sec.entities.AppUser;

import java.util.List;

public interface AccountService {
    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String username, String roleName);
    AppUser loadUserByUsername(String username);
    List<AppUser> listUsers();
}
