package com.project.salemanagement.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OtpDTO {
    @NotBlank(message = "Otp cannot blank!")
    String otp;
}
