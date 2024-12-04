package com.project.salemanagement.Services;

import com.project.salemanagement.dtos.StatusDTO;
import com.project.salemanagement.models.Status;

import java.util.List;

public interface IStatusService {
    List<Status> statusList();
    Status createStatus (StatusDTO statusDTO);
    Status deleteStatus (long id);
}
