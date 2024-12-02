package com.project.salemanagement.Repositories;

import com.project.salemanagement.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepo  extends JpaRepository<Company,Long> {
    Boolean existsByPhone(String phone);
    Boolean existsByEmail(String email);
    Boolean existsByCompanyName(String companyName);
}
