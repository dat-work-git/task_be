package com.project.salemanagement.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.salemanagement.response.ResponseData;


@RestController
@RequestMapping("salemanagement/v1/heath-check")
public class HeathCheckController {

    @GetMapping("")
    public ResponseData<?> HeathCheck() {
            return new ResponseData<>(HttpStatus.OK.value(), "Heathcheckok!");
        

    }
}