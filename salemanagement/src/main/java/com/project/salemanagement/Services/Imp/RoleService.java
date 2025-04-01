package com.project.salemanagement.Services.Imp;

import com.project.salemanagement.Repositories.RoleRepo;
import com.project.salemanagement.Services.IRoleService;
import com.project.salemanagement.dtos.RoleDTO;
import com.project.salemanagement.models.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RoleService implements IRoleService {
    private final RoleRepo roleRepo;
    @Override
    public Role createRole(RoleDTO roleDTO) {
        if(roleRepo.existsByName(roleDTO.getName())){
            throw new IllegalCallerException("Role is exist!");
        }
        Role role = Role.builder()
                .name(roleDTO.getName().toUpperCase())
                .description(roleDTO.getDescription())
                .build();
        return roleRepo.save(role);
    }

    @Override
    public List<Role> getRoleList() {
        List<Role> roleList = roleRepo.findAll();
        if(roleList.size() <=0 ){
            throw new IllegalCallerException("Empty!!!");
        }
        return roleList;
    }
}
