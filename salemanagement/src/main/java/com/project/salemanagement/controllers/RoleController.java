package com.project.salemanagement.controllers;

import com.project.salemanagement.Services.RoleService;
import com.project.salemanagement.dtos.CompanyDTO;
import com.project.salemanagement.dtos.RoleDTO;
import com.project.salemanagement.models.Role;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("salemanagement/v1/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    @PostMapping("")
    public ResponseEntity<?> createRole(@Valid @RequestBody RoleDTO roleDTO,
                                        BindingResult result){
        try{
            Role role = roleService.createRole(roleDTO);
            return ResponseEntity.ok().body(role);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("")
    public ResponseEntity<?> getAllRoles(){
        try{
            List<Role> roleList = roleService.getRoleList();
            return ResponseEntity.ok().body(roleList);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
