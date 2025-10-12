package com.project.salemanagement.controllers;

import com.project.salemanagement.Services.Imp.DashBoardService;
import com.project.salemanagement.dtos.CompanyDTO;
import com.project.salemanagement.dtos.DashBoardDTO;
import com.project.salemanagement.models.Company;
import com.project.salemanagement.models.DashBoard;
import com.project.salemanagement.response.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("salemanagement/v1/dashboard")
@RequiredArgsConstructor
public class DashBoardController {
    private final DashBoardService dashBoardService;

    @PostMapping("")
    public ResponseData<?> createPost(@Valid @RequestBody DashBoardDTO dashBoardDTO,
                                      BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> Error = result.getFieldErrors()
                        .stream()
                        .map(fieldError -> fieldError.getDefaultMessage())
                        .toList();
                return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), Error.toString());
            }
            DashBoard dashBoard = dashBoardService.createDashBoard(dashBoardDTO);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Created Post", dashBoard);
        } catch (Exception e) {
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }

    }

    @GetMapping("")
    public ResponseData<?> getPostByCountDay(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo
                                             ) {
        try {

            List<DashBoard> postList= dashBoardService.getPostByDay(dateFrom,dateTo);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Created Post", postList);
        } catch (Exception e) {
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }

    }
}
