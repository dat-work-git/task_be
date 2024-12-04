package com.project.salemanagement.controllers;
import com.project.salemanagement.Services.StatusService;
import com.project.salemanagement.dtos.StatusDTO;
import com.project.salemanagement.models.Status;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("salemanagement/v1/status")
public class StatusController {
    private final StatusService statusService;
    @PostMapping("")
    public ResponseEntity<?> createNew(@Valid @RequestBody StatusDTO statusDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> error = result.getFieldErrors().stream().map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage()).toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            Status status= statusService.createStatus(statusDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(status);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("")
    public ResponseEntity<?> getAllStatus() {
        try {
            List<Status> statusList = statusService.statusList();
            return ResponseEntity.ok().body(statusList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
