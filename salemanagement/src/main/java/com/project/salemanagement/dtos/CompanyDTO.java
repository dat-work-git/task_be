package com.project.salemanagement.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class CompanyDTO {
    @NotEmpty(message = "Customer Name can not be empty!")
    private String companyName;
    @NotEmpty(message = "Customer Name can not be empty!")
    private  String email;
    private  String phone;
    private  String assigned_person;
}
