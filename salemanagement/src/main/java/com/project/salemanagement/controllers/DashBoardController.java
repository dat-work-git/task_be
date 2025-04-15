package com.project.salemanagement.controllers;

import com.project.salemanagement.dtos.CompanyDTO;
import com.project.salemanagement.dtos.DashBoardDTO;
import com.project.salemanagement.models.Company;
import com.project.salemanagement.response.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("salemanagement/v1/dashboard")
@RequiredArgsConstructor
public class DashBoardController {
    @PostMapping("")
    public ResponseData<?> createCompany(@Valid @RequestBody DashBoardDTO dashBoardDTO,
                                         BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> Error = result.getFieldErrors()
                        .stream()
                        .map(fieldError -> fieldError.getDefaultMessage())
                        .toList();
                return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), Error.toString());
            }
            return new ResponseData<>(HttpStatus.CREATED.value(),"Created Post",dashBoardDTO);
        } catch (Exception e) {
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }

    }
}
