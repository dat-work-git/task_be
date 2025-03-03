package com.project.salemanagement.controllers;

import com.project.salemanagement.Services.ResetPasswordService;
import com.project.salemanagement.dtos.OtpDTO;
import com.project.salemanagement.dtos.ResetPasswordDTO;
import com.project.salemanagement.models.ResetPassword;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> confirmOtp(@Valid @RequestBody OtpDTO otpDTO, BindingResult result) throws Exception {
        try {
            if (result.hasErrors()) {
                List<String> error = result.getFieldErrors().stream()
                        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage()).toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            Boolean isConfirm = resetPasswordService.confirmOtp(otpDTO.getOtp());
            return ResponseEntity.ok(Map.of("isConfirm",isConfirm));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
