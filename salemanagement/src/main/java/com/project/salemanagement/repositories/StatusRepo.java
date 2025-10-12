package com.project.salemanagement.repositories;

import com.project.salemanagement.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepo extends JpaRepository<Status,Long> {
}
