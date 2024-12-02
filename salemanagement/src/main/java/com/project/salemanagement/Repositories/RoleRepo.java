package com.project.salemanagement.Repositories;

import com.project.salemanagement.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
    Boolean existsByName(String name);
}
