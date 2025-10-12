package com.project.salemanagement.repositories;

import com.project.salemanagement.models.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ResetPasswordRepo  extends JpaRepository<ResetPassword,Long> {
    ResetPassword findByOtpCode(String otpCode);
}
