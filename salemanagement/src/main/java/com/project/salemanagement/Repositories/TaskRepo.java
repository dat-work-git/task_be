package com.project.salemanagement.Repositories;


import com.project.salemanagement.models.Company;
import com.project.salemanagement.models.Task;
import com.project.salemanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {
    List<Task> findByCompany(Company company);
}
