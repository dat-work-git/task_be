package com.project.salemanagement.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashBoardDTO {
    @NotEmpty(message = "Title can not be empty!")
    private String title;
    private String description;
    @NotEmpty(message = "Email can not be empty!")
    private String emailUser;
}
