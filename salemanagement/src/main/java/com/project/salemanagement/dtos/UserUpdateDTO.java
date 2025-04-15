package com.project.salemanagement.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDTO {
    @JsonProperty("fullname")
    private String fullName;
    @NotBlank(message = "Email can not be empty!")
    private  String email;
    private String phone;
    private String address;
    @NotNull(message = "Role can not be empty!")
    private Long roleId;
    private int is_active;
}
