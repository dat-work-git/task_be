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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.sql.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskService implements ITasksService {
    private final CompanyRepo companyRepo;
    private final UserRepo userRepo;
    private final TaskRepo taskRepo;
    private final StatusRepo statusRepo;

    @Override
    public Task createTask(TaskDTO taskDTO) {
        Company company = companyRepo.findById(taskDTO.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Company with id:" + taskDTO.getCompanyId()));
        User user = userRepo.findByEmail(taskDTO.getAssign())
                .orElseThrow(() -> new IllegalArgumentException("Cannot find User with id:" + taskDTO.getAssign()));
        Status status = statusRepo.findById(taskDTO.getStatus())
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Status with id:" + taskDTO.getAssign()));
        Task task = Task.builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .action(taskDTO.getAction())
                .company(company)
                .urgent(taskDTO.getUrgent())
                .startDate(taskDTO.getStartDate())
                .completedDate(taskDTO.getCompletedDate())
                .assignedUser(user)
                .status(status)
                .build();
        taskRepo.save(task);
        return task;
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
        User user = userRepo.findByEmail(taskDTO.getAssign())
                .orElseThrow(()->new InvalidParameterException("Cannot Found User"));
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Task "));
        Status status = statusRepo.findById(taskDTO.getStatus())
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Status with id:" + taskDTO.getAssign()));
        task.setAction(taskDTO.getAction());
        task.setCompany(company);
        task.setUrgent(taskDTO.getUrgent());
        task.setStatus(status);
        task.setAssignedUser(user);
        taskRepo.save(task);
        return task;
    }

    @Override
    public void deleteTask(long id) {

    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }
}
