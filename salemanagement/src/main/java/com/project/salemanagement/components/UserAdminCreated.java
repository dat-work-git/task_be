package com.project.salemanagement.components;

import com.project.salemanagement.models.Role;
import com.project.salemanagement.models.User;
import com.project.salemanagement.repositories.RoleRepo;
import com.project.salemanagement.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class UserAdminCreated implements CommandLineRunner {

    @Autowired
    private UserRepo userRepository;
    @Autowired
    private RoleRepo roleRepo;

    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if(userRepository.findByEmail("lvtd2311@gmail.com").isEmpty()) {
            User admin = new User();
            admin.setEmail("lvtd2311@gmail.com");
            admin.setPassword(passwordEncoder.encode("tdat23112003!!"));
            admin.setName("Admin");
            admin.setPhone("0123456789");
            admin.setIs_active("1");
            Role role = roleRepo.findById(1L).orElseThrow(() -> new RuntimeException("Role not found"));
            admin.setRole(role);
            Date now = new Date();
            admin.setCreatedAt(now);
            admin.setUpdatedAt(now);
            userRepository.save(admin);
        }
    }
}
