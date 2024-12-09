package com.project.salemanagement.controllers;

import com.project.salemanagement.Services.UserService;
import com.project.salemanagement.dtos.UserDTO;
import com.project.salemanagement.dtos.UserLoginDTO;
import com.project.salemanagement.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("salemanagement/v1/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
        try {

            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors().stream().map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage()).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
                // đổi sang báo cả status
                return ResponseEntity.badRequest().body("Wrong Retype Password");
            }
            User userRegister = userService.createUser(userDTO);
            if (userRegister == null) {
                return ResponseEntity.badRequest().body("Email is exist");
            }
            ;
            return ResponseEntity.ok(userRegister);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO,
                                       BindingResult result,
                                       HttpServletRequest httpServletRequest) {
        try {
            String token = userService.login(userLoginDTO.getEmail(),
                    userLoginDTO.getPassword(),
                    userLoginDTO.getRoleId());
            return ResponseEntity.ok(
//                    LoginResponse.builder()
//                    .message("Login Successfully!")
//                    .token(token)
//                    .build()
                    token
            );
        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(
////                    LoginResponse.builder()
////                    .message(localizationUtils.getLocalizationMessages(MessageKey.LOGIN_FAILED, e.getMessage()))
////                    .build()
//
//            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            //.status(HttpStatus.BAD_REQUEST)//
        }
    }

}
