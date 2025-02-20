package com.project.salemanagement.Services;
import com.project.salemanagement.Repositories.CompanyRepo;
import com.project.salemanagement.Repositories.StatusRepo;
import com.project.salemanagement.Repositories.TaskRepo;
import com.project.salemanagement.Repositories.UserRepo;
import com.project.salemanagement.dtos.TaskDTO;
import com.project.salemanagement.models.Company;
import com.project.salemanagement.models.Status;
import com.project.salemanagement.models.Task;
import com.project.salemanagement.models.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskService implements ITasksService {
    private final CompanyRepo companyRepo;
    private final UserRepo userRepo;
    private final TaskRepo taskRepo;
    private final StatusRepo statusRepo;

    @Transactional
    @Override
    public List<Task> createTask(TaskDTO taskDTO) {
        Company company = companyRepo.findById(taskDTO.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Company with id:" + taskDTO.getCompanyId()));
        // danh sach user
        List<User> userList = userRepo.findByEmailIn(taskDTO.getAssignedUsers());
        if (userList.isEmpty()){
            throw new IllegalArgumentException("User List empty!");
        }
        Long statusId = taskDTO.getStatus();
        Status status = statusRepo.findById(statusId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Status with id:" + taskDTO.getAssignedUsers()));
        Task task = Task.builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .action(taskDTO.getAction())
                .company(company)
                .urgent(taskDTO.getUrgent())
                .startDate(taskDTO.getStartDate())
                .completedDate(taskDTO.getCompletedDate())
                .assignedUsers(userList)
                .status(status)
                .build();
        taskRepo.save(task);
        List<Task> taskList = taskByCompanyId(taskDTO.getCompanyId());
        return taskList;
    }

    @Override
    public Task getTask(long id) {
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Task " ));
        return task;
    }

    @Override
    public List<Task> taskByCompanyId(long companyId) {
        Company company = companyRepo.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Task " ));
        List<Task> taskList = taskRepo.findByCompany(company);
        return taskList;
    }

    @Override
    public Task updateTask(long id, TaskDTO taskDTO) {
        Company company = companyRepo.findById(taskDTO.getCompanyId())
                .orElseThrow(()->new InvalidParameterException("Cannot Found Company!"));
        List<User> userList = userRepo.findByEmailIn(taskDTO.getAssignedUsers());
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Task"));
        Status status = statusRepo.findById(taskDTO.getStatus())
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Status with id:" + taskDTO.getAssignedUsers()));
        task.setTitle(taskDTO.getTitle());
        task.setAction(taskDTO.getAction());
        task.setCompany(company);
        task.setUrgent(taskDTO.getUrgent());
        task.setStatus(status);
        task.setAssignedUsers(userList);
        taskRepo.save(task);
        return task;
    }

    @Transactional
    @Override
    public Long deleteTask(long id) {
    Task task = taskRepo.findById(id).orElseThrow(()->new EntityNotFoundException("Task Not Found!"));
    taskRepo.delete(task);
    return id;
    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }
}
