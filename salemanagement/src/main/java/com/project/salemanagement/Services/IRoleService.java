package com.project.salemanagement.Services;

import com.project.salemanagement.dtos.RoleDTO;
import com.project.salemanagement.models.Role;

import java.util.List;

public interface IRoleService {
    Role createRole (RoleDTO roleDTO);
    List<Role> getRoleList();
}
