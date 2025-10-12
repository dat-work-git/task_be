package com.project.salemanagement.Services.Imp;

import com.project.salemanagement.repositories.CompanyRepo;
import com.project.salemanagement.repositories.StatusRepo;
import com.project.salemanagement.repositories.TaskRepo;
import com.project.salemanagement.repositories.UserRepo;
import com.project.salemanagement.Services.ITasksService;
import com.project.salemanagement.dtos.TaskDTO;
import com.project.salemanagement.models.Company;
import com.project.salemanagement.models.Status;
import com.project.salemanagement.models.Task;
import com.project.salemanagement.models.User;
import com.project.salemanagement.response.PageResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService implements ITasksService {
    private final CompanyRepo companyRepo;
    private final UserRepo userRepo;
    private final TaskRepo taskRepo;
    private final StatusRepo statusRepo;


    @Transactional
    @Override
    public List<Task> createTask(TaskDTO taskDTO) throws Exception {
        Company company = companyRepo.findById(taskDTO.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Company with id:" + taskDTO.getCompanyId()));
        // danh sach user
        List<User> userList = userRepo.findByEmailIn(taskDTO.getAssignedUsers());

        if (userList.isEmpty()) {
            throw new IllegalArgumentException("User List empty!");
        }

        Long statusId = taskDTO.getStatus();

        Status status = statusRepo.findById(statusId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Status with id:" + taskDTO.getAssignedUsers()));

        Task parentTask = null;
        if (taskDTO.getParentTask() != null && taskDTO.getParentTask() > 0) {
            parentTask = taskRepo.findById(taskDTO.getParentTask())
                    .orElseThrow(() -> new Exception("Cannot find Parent task!"));
        }

//        List<Long> taskSubList = taskRepo.findByParent(parentTask).stream().map(Task::getId).collect(Collectors.toList());

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
                .parent(parentTask != null ? parentTask : null)
                .build();
        taskRepo.save(task);
        List<Task> taskList = taskByCompanyId(taskDTO.getCompanyId());
        return taskList;
    }

    @Override
    public Task getTask(long id) {
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Task "));
        return task;
    }

    @Override
    public List<Task> taskByCompanyId(long companyId) {
        Company company = companyRepo.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Task "));
        List<Task> taskList = taskRepo.findByCompanyAndParentIsNull(company);
        return taskList;
    }

    @Override
    public PageResponse<?> getAllTaskAdmin(int pageNo,
                                           int pageSize,
                                           String... sorts // java 11 có
    ) {
        List<Sort.Order> orders = new ArrayList<>();
        for (String sortBy : sorts) {
            if (StringUtils.hasLength(sortBy)) {
                //id:asc|desc
                Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
                Matcher matcher = pattern.matcher(sortBy);
                if (matcher.find()) {
                    if (matcher.group(3).equalsIgnoreCase("asc")) {
                        orders.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                    }
                    if (matcher.group(3).equalsIgnoreCase("desc")) {
                        orders.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                    }
                }
            }
        }
        Pageable pageableUser = PageRequest.of(pageNo,
                pageSize,
                Sort.by(orders)
        );
        Page<Task> taskPage = taskRepo.findAll(pageableUser);

        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(taskPage.getTotalPages())
                .items(taskPage.getContent())
                .build();
    }

    @Override
    public List<Task> updateTask(long id, TaskDTO taskDTO) {
        Company company = companyRepo.findById(taskDTO.getCompanyId())
                .orElseThrow(() -> new InvalidParameterException("Cannot Found Company!"));
        List<User> userList = userRepo.findByEmailIn(taskDTO.getAssignedUsers());
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Task"));
        Status status = statusRepo.findById(taskDTO.getStatus())
                .orElseThrow(() -> new IllegalArgumentException("Cannot find Status with id:" + taskDTO.getAssignedUsers()));
        Task parentTask = taskRepo.findById((taskDTO.getParentTask() != null && taskDTO.getParentTask() > 0) ? taskDTO.getParentTask() : 0).orElse(
                null
        );
        List<Long> taskSubList = taskRepo.findByParent(parentTask).stream().map(Task::getId).collect(Collectors.toList());
        task.setTitle(taskDTO.getTitle());
        task.setAction(taskDTO.getAction());
        task.setDescription(taskDTO.getDescription());
        task.setCompany(company);
        task.setUrgent(taskDTO.getUrgent());
        task.setStatus(status);
        task.setAssignedUsers(userList);
        task.setParent(parentTask);
        task.setStartDate(taskDTO.getStartDate());
        task.setCompletedDate(taskDTO.getCompletedDate());
        taskRepo.save(task);
        List<Task> taskList = taskByCompanyId(taskDTO.getCompanyId());
        return taskList;
    }

    @Transactional
    @Override
    public Long deleteTask(long id) {
        Task task = taskRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Task Not Found!"));
        taskRepo.delete(task);
        return id;
    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }
}
