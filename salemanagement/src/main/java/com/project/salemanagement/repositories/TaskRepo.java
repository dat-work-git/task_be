package com.project.salemanagement.repositories;


import com.project.salemanagement.models.Company;
import com.project.salemanagement.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {
    List<Task> findByCompanyAndParentIsNull(Company company);
    List<Task> findByParent(Task task);
    Page<Task> findAllByParentIsNull(Pageable pageable);

}
