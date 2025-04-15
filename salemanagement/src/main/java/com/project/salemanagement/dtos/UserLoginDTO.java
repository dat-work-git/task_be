package com.project.salemanagement.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginDTO {
    @NotBlank(message = "Email can not be empty!")
    private  String email;
    @NotBlank(message = "Password can not be empty!")
    private String password;
    @NotNull(message = "Password can not be empty!")
    private Long roleId;
}
