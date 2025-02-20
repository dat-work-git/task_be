package com.project.salemanagement.Services;

import com.project.salemanagement.Repositories.ResetPasswordRepo;
import com.project.salemanagement.Repositories.UserRepo;
import com.project.salemanagement.dtos.ResetPasswordDTO;
import com.project.salemanagement.models.ResetPassword;
import com.project.salemanagement.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResetPasswordService implements IResetPasswordService{
    private final UserRepo userRepo;
    private final ResetPasswordRepo resetPasswordRepo;
    @Override
    public ResetPassword createOtp(ResetPasswordDTO resetPasswordDTO) throws Exception {
        User user = userRepo.findByEmail(resetPasswordDTO.getEmail())
                .orElseThrow(() -> new Exception("Cannot find Email!"));
        String otp = UUID.randomUUID().toString()
                            .replaceAll("[^0-9]", "")
                            .substring(0,6);

        ResetPassword resetPasswordOTP =  ResetPassword.builder()
                .userId(user.getId())
                .email(resetPasswordDTO.getEmail())
                .otpCode(otp)
                .expireTime(Instant.now().plus(75, ChronoUnit.SECONDS))
                .build();
        resetPasswordRepo.save(resetPasswordOTP);
        return resetPasswordOTP;
    }

    @Override
    public Boolean confirmOtp(String otpConfirm) throws Exception {

        ResetPassword  resetPassword = resetPasswordRepo.findByOtpCode(otpConfirm);
        if(resetPassword == null){
            throw new Exception("Invalid!");
        }
        if(resetPassword.getExpireTime().isBefore(Instant.now())){
            throw new Exception("Invalid!!");
        }

        return true;
    }

}
//0.0- 0.999999
