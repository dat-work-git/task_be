package com.project.salemanagement.Repositories;

import com.project.salemanagement.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepo  extends JpaRepository<Company,Long> {
    Boolean existsByPhone(String phone);
    Boolean existsByEmail(String email);
    Boolean existsByCompanyName(String companyName);
    List<Company> findByAssignedPerson_Email(String email);
    @Query(value = "SELECT DISTINCT c.* " +
            "FROM company c " +
            "JOIN tasks t ON c.id = t.company_id " +
            "JOIN task_assigned_users tau ON t.id = tau.task_id " +
            "WHERE tau.user_id = :userId", nativeQuery = true)
    List<Company> findConpanyListByUserAssign(long userId);

}
