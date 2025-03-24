package com.project.salemanagement.Services;

import com.project.salemanagement.dtos.CompanyDTO;
import com.project.salemanagement.models.Company;
import com.project.salemanagement.response.PageResponse;

import java.util.List;

public interface ICompanyService {
    PageResponse<?> getAllCompany(int pageNo,
                               int pageSize,
                               String... sorts);
    List<Company> getCompanyByAssignEmail(String AssignEmail);
    Company createCompany(CompanyDTO companyDTO);
    Company updateCompany(Long id,CompanyDTO companyDTO);
    Company getCompany (Long id);
    PageResponse<?>  deleteCompany( Long CompanyId,int pageNo,
                                 int pageSize,
                                 String... sorts);
}
