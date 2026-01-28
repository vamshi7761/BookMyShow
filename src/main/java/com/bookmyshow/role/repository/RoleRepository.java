package com.bookmyshow.role.repository;

import com.bookmyshow.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
    boolean existsByRoleName(String roleName);
}