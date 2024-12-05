package com.project.salemanagement.Repositories;

import com.project.salemanagement.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepo  extends JpaRepository<Company,Long> {
    Boolean existsByPhone(String phone);
    Boolean existsByEmail(String email);
    Boolean existsByCompanyName(String companyName);
    List<Company> findByAssignedPerson_Email(String email);

}
