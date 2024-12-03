package com.project.salemanagement.controllers;

import com.project.salemanagement.Repositories.TaskRepo;
import com.project.salemanagement.Services.TaskService;
import com.project.salemanagement.dtos.TaskDTO;
import com.project.salemanagement.models.Task;
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
@RequiredArgsConstructor
@RequestMapping("salemanagement/v1/tasks")
public class TaskController {
    private final TaskService taskService;
    @PostMapping("")
    public ResponseEntity<?> createNew(@Valid @RequestBody TaskDTO taskDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> error = result.getFieldErrors().stream().map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage()).toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            Task task = taskService.createTask(taskDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(task);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
