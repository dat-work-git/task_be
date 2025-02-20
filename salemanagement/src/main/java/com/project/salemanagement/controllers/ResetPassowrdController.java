package com.project.salemanagement.controllers;

import com.project.salemanagement.Services.ResetPasswordService;
import com.project.salemanagement.dtos.ResetPasswordDTO;
import com.project.salemanagement.models.ResetPassword;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("salemanagement/v1/resetPassword")
@RequiredArgsConstructor
public class ResetPassowrdController {
    private final ResetPasswordService resetPasswordService;
    @PostMapping("/renderOtp")
    public ResponseEntity<?> createOtp(@RequestBody ResetPasswordDTO resetPasswordDTO) throws Exception {
        try {
            ResetPassword resetPassword = resetPasswordService.createOtp(resetPasswordDTO);
            return ResponseEntity.ok(resetPassword);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/confirmOtp")
    public ResponseEntity<?> confirmOtp(@RequestParam String otpConfirm) throws Exception {
        try {
            Boolean isConfirm = resetPasswordService.confirmOtp(otpConfirm);
            return ResponseEntity.ok(isConfirm);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
