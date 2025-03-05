package com.project.salemanagement.Services;
import com.project.salemanagement.Repositories.ResetPasswordRepo;
import com.project.salemanagement.Repositories.UserRepo;
import com.project.salemanagement.dtos.ResetPasswordDTO;
import com.project.salemanagement.models.ResetPassword;
import com.project.salemanagement.models.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.security.SecureRandom;
@Service
@RequiredArgsConstructor
public class ResetPasswordService implements IResetPasswordService{
    private final UserRepo userRepo;
    private final ResetPasswordRepo resetPasswordRepo;
    private final SendEmail sendEmail;
    private final PasswordEncoder passwordEncoder;
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%^&*!";

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
        sendEmail.sendEmail(resetPasswordOTP.getEmail(),
                "Task Management Reset Password",
                "OTP: "+otp,
                null
                );
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
        User user = userRepo.findByEmail(resetPassword.getEmail()).
                orElseThrow(()-> new EntityNotFoundException("Cannot find email!"));
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(randomIndex));
        }
        sendEmail.sendEmail(user.getEmail(),
                "Task Management Reset Password",
                "New Password: "+password
                        + " .Please log in and change your password immediately, then delete this email"
                ,
                null
        );
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);
        return true;
    }

}
