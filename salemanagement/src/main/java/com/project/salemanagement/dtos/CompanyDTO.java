package com.project.salemanagement.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyDTO {
    @NotEmpty(message = "Customer Name can not be empty!")
    private String companyName;
    @NotEmpty(message = "Email can not be empty!")
    private  String email;
    private  String phone;
    private String assigned_person;
}
