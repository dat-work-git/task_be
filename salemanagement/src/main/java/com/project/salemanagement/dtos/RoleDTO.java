package com.project.salemanagement.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDTO {
    @NotEmpty(message = "Role e can not be empty!")
    private String name;
    private  String description;
}
