package com.project.salemanagement.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordDTO {
    @NotEmpty(message = "Email can not be empty!")
    private  String email;
}
