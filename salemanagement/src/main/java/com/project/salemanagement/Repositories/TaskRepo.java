package com.project.salemanagement.Repositories;


import com.project.salemanagement.models.Task;
import com.project.salemanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, Long> {
}
