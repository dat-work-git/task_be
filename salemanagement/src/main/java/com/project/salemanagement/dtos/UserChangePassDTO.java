package com.project.salemanagement.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserChangePassDTO {
        @NotBlank(message = "Password can not be empty!")
        private String oldPassword;
        @NotBlank(message = "Password can not be empty!")
        private String newPassword;
        @NotBlank(message = "Retype Password can not be empty!")
        private  String retypePassword;

}
