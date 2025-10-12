package com.project.salemanagement.controllers;
import com.project.salemanagement.Services.Imp.TaskService;
import com.project.salemanagement.dtos.TaskDTO;
import com.project.salemanagement.models.Task;
import com.project.salemanagement.response.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("salemanagement/v1/tasks")
public class TaskController {
    private final TaskService taskService;
    @PostMapping("/createTask")
    public ResponseEntity<?> createNew(@Valid @RequestBody TaskDTO taskDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> error = result.getFieldErrors().stream()
                        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage()).toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            List<Task> taskList = taskService.createTask(taskDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(taskList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/taskId") // lấy danh sách order của user theo id
    public ResponseEntity<?> getTaskById(@Valid @RequestParam("taskId") long taskId) {
        try {
            Task task = taskService.getTask(taskId);
            return ResponseEntity.ok().body(task);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/admin/list-task") // lấy danh sách order của user theo id
    public ResponseEntity<?> getTaskByAdmin( @RequestParam("pageNo") int pageNo,
                                            @RequestParam("pageSize") int pageSize,
                                             @RequestParam(required = false) String... sortBy
                                            ) {
        try {
            PageResponse<?> pageResponseTask = taskService.getAllTaskAdmin(pageNo,pageSize, sortBy);
            return ResponseEntity.ok().body(pageResponseTask);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/company")
    public ResponseEntity<?> getTasksByCompanyId( @RequestParam long companyId) {
        try {
            List<Task> task = taskService.taskByCompanyId(companyId);
            return ResponseEntity.ok().body(task);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask ( @PathVariable long taskId ){
        try {
            long taskIdDelete = taskService.deleteTask(taskId);
            return ResponseEntity.ok().body(taskIdDelete);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }    }

    @PutMapping("/{taskId}")
    public ResponseEntity<?> updateTask(@Valid @RequestBody TaskDTO taskDTO,@PathVariable long taskId, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> error = result.getFieldErrors().stream()
                        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage()).toList();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            List<Task> taskList = taskService.updateTask(taskId,taskDTO);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(taskList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
