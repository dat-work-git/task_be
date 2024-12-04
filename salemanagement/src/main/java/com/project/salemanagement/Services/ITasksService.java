package com.project.salemanagement.Services;

import com.project.salemanagement.dtos.TaskDTO;
import com.project.salemanagement.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ITasksService {
    Task createTask(TaskDTO taskDTO) ;
    Task getTask(long id);
    List<Task> taskByCompanyId(long companyId);
    //Page<ProductResponse> getAllProducts(String keyword, long categoryId, PageRequest pageRequest);
    Task updateTask(long id, TaskDTO taskDTO);
    void deleteTask(long id);
    boolean existsByName(String name);
    //ProductImage createProductImage(long productId, ProductImagesDTO productImagesDTO);
}
