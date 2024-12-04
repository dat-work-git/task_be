package com.project.salemanagement.Repositories;

import com.project.salemanagement.models.Role;
import com.project.salemanagement.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepo extends JpaRepository<Status,Long> {
}
