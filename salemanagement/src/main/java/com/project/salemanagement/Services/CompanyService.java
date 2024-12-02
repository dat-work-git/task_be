package com.project.salemanagement.Services;

import com.project.salemanagement.Repositories.CompanyRepo;
import com.project.salemanagement.dtos.CompanyDTO;
import com.project.salemanagement.models.Company;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
@Service
@AllArgsConstructor
public class CompanyService implements ICompanyService{
    private  final CompanyRepo companyRepo;
    @Override
    public List<Company> getAllCompany() {
        try {
            List<Company> companyList = companyRepo.findAll();
            return companyList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Company createCompany(CompanyDTO companyDTO) {
        if(companyRepo.existsByPhone(companyDTO.getPhone())||
                companyRepo.existsByEmail(companyDTO.getEmail())||
                companyRepo.existsByCompanyName(companyDTO.getCompanyName())){
            throw new InvalidParameterException("Company, phone or email was exist");
        }
        Company company = Company.builder()
                .companyName(companyDTO.getCompanyName())
                .email(companyDTO.getEmail())
                .phone(companyDTO.getPhone())
                .assgined_person(companyDTO.getAssigned_person())
                .build();
        return companyRepo.save(company);
    }

    @Override
    // chua dung duoc
    public Company updateCompany(Long id,CompanyDTO companyDTO) {
        Company company = getCompany(id);
        company.setCompanyName(companyDTO.getCompanyName());
        company.setEmail(companyDTO.getEmail());
        company.setPhone(companyDTO.getPhone());
        company.setAssgined_person(companyDTO.getAssigned_person());
        return companyRepo.save(company);
    }

    @Override
    public Company getCompany(Long id) {
        Company company = companyRepo.findById(id).orElseThrow(()->new IllegalCallerException("Can not find company"));
        return company;
    }
}
