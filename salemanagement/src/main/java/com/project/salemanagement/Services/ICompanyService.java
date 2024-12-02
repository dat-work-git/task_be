package com.project.salemanagement.Services;

import com.project.salemanagement.dtos.CompanyDTO;
import com.project.salemanagement.models.Company;

import java.util.List;

public interface ICompanyService {
    List<Company> getAllCompany();
    Company createCompany(CompanyDTO companyDTO);
    Company updateCompany(Long id,CompanyDTO companyDTO);
    Company getCompany (Long id);
}
