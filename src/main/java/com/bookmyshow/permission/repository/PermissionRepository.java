package com.bookmyshow.permission.repository;

import com.bookmyshow.permission.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {

    boolean existsByPermissionName(String permissionName);
}
