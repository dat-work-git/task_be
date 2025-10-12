package com.project.salemanagement.Services;

import com.project.salemanagement.dtos.TaskDTO;
import com.project.salemanagement.models.Task;
import com.project.salemanagement.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ITasksService {
    List<Task> createTask(TaskDTO taskDTO) throws Exception;
    Task getTask(long id);
    List<Task> taskByCompanyId(long companyId);
    PageResponse<?> getAllTaskAdmin(int pageNo, int pageSize, String... sorts);
    List<Task> updateTask(long id, TaskDTO taskDTO);
    Long deleteTask(long id);
    boolean existsByName(String name);
    //ProductImage createProductImage(long productId, ProductImagesDTO productImagesDTO);

}
