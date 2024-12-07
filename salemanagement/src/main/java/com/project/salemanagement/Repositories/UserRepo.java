package com.project.salemanagement.Repositories;
import com.project.salemanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Boolean existsByEmail (String email);
    Optional<User> findByEmail(String email);
    List<User> findByEmailIn(List<String> emails);
}
