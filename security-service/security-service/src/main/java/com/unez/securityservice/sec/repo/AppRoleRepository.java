package com.unez.securityservice.sec.repo;

import com.unez.securityservice.sec.entities.AppRole;
import com.unez.securityservice.sec.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    AppRole findByRoleName(String roleName);
}
