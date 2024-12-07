package com.project.salemanagement.Services;

import com.project.salemanagement.Repositories.CompanyRepo;
import com.project.salemanagement.Repositories.UserRepo;
import com.project.salemanagement.dtos.CompanyDTO;
import com.project.salemanagement.models.Company;
import com.project.salemanagement.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
@AllArgsConstructor
public class CompanyService implements ICompanyService {
    private final CompanyRepo companyRepo;
    private final UserRepo userRepo;

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
    public List<Company> getCompanyByAssignEmail(String AssignEmail) {
        User user = userRepo.findByEmail(AssignEmail)
                .orElseThrow(() -> new IllegalCallerException("Cannot find email"));
        if (user.getRole().getName().toUpperCase().equals("USER")) {
            List<Company> companyList = companyRepo.findConpanyListByUserAssign(user.getId());
            return companyList;
        }
        if (user.getRole().getName().toUpperCase().equals("ADMIN")) {
            List<Company> companyList = companyRepo.findAll();
            return companyList;
        }

        return null;
    }

    @Override
    public Company createCompany(CompanyDTO companyDTO) {
        if (companyRepo.existsByPhone(companyDTO.getPhone()) ||
                companyRepo.existsByEmail(companyDTO.getEmail()) ||
                companyRepo.existsByCompanyName(companyDTO.getCompanyName())) {
            throw new InvalidParameterException("Company, phone or email was exist");
        }
        User user = userRepo.findByEmail(companyDTO.getAssigned_person())
                .orElseThrow(() -> new IllegalCallerException("Can not find User"));
        Company company = Company.builder()
                .companyName(companyDTO.getCompanyName())
                .email(companyDTO.getEmail())
                .phone(companyDTO.getPhone())
                .assignedPerson(user)
                .build();
        return companyRepo.save(company);
    }

    @Override
    // chua dung duoc
    public Company updateCompany(Long id, CompanyDTO companyDTO) {
        Company company = getCompany(id);
        User user = userRepo.findByEmail(companyDTO.getAssigned_person())
                .orElseThrow(() -> new IllegalCallerException("Can not find User"));
        company.setCompanyName(companyDTO.getCompanyName());
        company.setEmail(companyDTO.getEmail());
        company.setPhone(companyDTO.getPhone());
        company.setAssignedPerson(user);
        return companyRepo.save(company);
    }

    @Override
    public Company getCompany(Long id) {
        Company company = companyRepo.findById(id).orElseThrow(() -> new IllegalCallerException("Can not find company"));
        return company;
    }
}
