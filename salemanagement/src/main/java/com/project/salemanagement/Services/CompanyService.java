package com.project.salemanagement.Services;
import com.project.salemanagement.Repositories.CompanyRepo;
import com.project.salemanagement.Repositories.UserRepo;
import com.project.salemanagement.dtos.CompanyDTO;
import com.project.salemanagement.models.Company;
import com.project.salemanagement.models.Task;
import com.project.salemanagement.models.User;
import com.project.salemanagement.response.PageResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class CompanyService implements ICompanyService {
    private final CompanyRepo companyRepo;
    private final UserRepo userRepo;

    @Override
    public PageResponse<?> getAllCompany(int pageNo,
                                       int pageSize,
                                       String... sorts) {
        try {
            List<Sort.Order> orders = new ArrayList<>();
            for(String sortBy: sorts) {
                if (StringUtils.hasLength(sortBy)) {
                    //id:asc|desc
                    Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
                    Matcher matcher = pattern.matcher(sortBy);
                    if (matcher.find()) {
                        if (matcher.group(3).equalsIgnoreCase("asc")) {
                            orders.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                        }
                        if (matcher.group(3).equalsIgnoreCase("desc")) {
                            orders.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                        }
                    }
                }
            }
            Pageable pageableUser = PageRequest.of(pageNo,
                    pageSize,
                    Sort.by(orders)
            );
            Page<Company> companyPage = companyRepo.findAll(pageableUser);

            return PageResponse.builder()
                    .pageNo(pageNo)
                    .pageSize(pageSize)
                    .totalPage(companyPage.getTotalPages())
                    .items(companyPage.getContent())
                    .build();
         } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Company> getCompanyByAssignEmail(String AssignEmail) {
        User user = userRepo.findByEmail(AssignEmail)
                .orElseThrow(() -> new IllegalCallerException("Cannot find email"));
//        if (user.getRole().getName().toUpperCase().equals("USER")) {
//            List<Company> companyList = companyRepo.findConpanyListByUserAssign(user.getId());
//            return companyList;
//        }
//        if (user.getRole().getName().toUpperCase().equals("ADMIN")) {
//            List<Company> companyList = companyRepo.findAll();
//            return companyList;
//        }
        List<Company> companyList = companyRepo.findAll();
            return companyList;

//        return null;
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
    public Company getCompany(Long CompanyId) {
        Company company = companyRepo.findById(CompanyId)
                .orElseThrow(() -> new IllegalCallerException("Can not find company"));
        return company;
    }

    @Override
    public PageResponse<?> deleteCompany(Long CompanyId,int pageNo,
                                       int pageSize,
                                       String... sorts) {
        Company company = companyRepo.findById(CompanyId)
                .orElseThrow(() -> new IllegalCallerException("Can not find company"));
        companyRepo.delete(company);
        PageResponse<?> companyPage = getAllCompany(pageNo,pageSize,sorts);
        return companyPage;
    }
}
