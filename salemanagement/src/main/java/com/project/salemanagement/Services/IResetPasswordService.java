package com.project.salemanagement.Services;


import com.project.salemanagement.dtos.ResetPasswordDTO;
import com.project.salemanagement.models.ResetPassword;

public interface IResetPasswordService {
    ResetPassword createOtp(ResetPasswordDTO resetPasswordDTO) throws Exception;
    Boolean confirmOtp(String otpConfirm) throws Exception;
}
