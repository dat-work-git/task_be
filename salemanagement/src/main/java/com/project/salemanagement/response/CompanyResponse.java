package com.project.salemanagement.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.salemanagement.models.Company;
import com.project.salemanagement.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
@Builder
public class CompanyResponse {
    Long id;
    private String companyName;
    private String email;
    private String phone;
    private UserResponse assignedPerson;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date updateAt;
    public static List<CompanyResponse> fromListCompany(List<Company> companyList){
        List<CompanyResponse> companyResponseList = new ArrayList<>();
        for (Company company : companyList){
            UserResponse userResponse = UserResponse.builder()
                    .id(company.getAssignedPerson().getId())
                    .name(company.getAssignedPerson().getName())
                    .email(company.getAssignedPerson().getEmail())
                    .phone(company.getAssignedPerson().getPhone())
                    .address(company.getAssignedPerson().getAddress())
                    .role(company.getAssignedPerson().getRole())
                    .build();
            CompanyResponse companyResponse = CompanyResponse.builder()
                    .id(company.getId())
                    .companyName(company.getCompanyName())
                    .email(company.getEmail())
                    .phone(company.getPhone())
                    .assignedPerson(userResponse)
                    .createdAt(company.getCreated_at())
                    .updateAt(company.getUpdated_at())
                    .build();
            companyResponseList.add(companyResponse);
        }
        return companyResponseList;
    }
}
