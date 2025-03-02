package com.project.salemanagement.Services;

import com.project.salemanagement.Repositories.StatusRepo;
import com.project.salemanagement.dtos.StatusDTO;
import com.project.salemanagement.models.Status;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class StatusService implements IStatusService{
    private final StatusRepo statusRepo;
    @Override
    public List<Status> statusList() {
        List<Status> statusList = statusRepo.findAll();
        return statusList;
    }

    @Override
    public Status createStatus(StatusDTO statusDTO) {
        Status status = Status.builder()
                .name(statusDTO.getName())
                .color(statusDTO.getColor())
                .build();
        statusRepo.save(status);
        return status;
    }

    @Override
    public Status deleteStatus(long id) {
        return null;
    }

    @Override
    public Status updateStatus(StatusDTO statusDTO, long statusId) throws Exception {
        Status status = statusRepo.findById(statusId).
                orElseThrow(()-> new EntityNotFoundException("Cannot find Status"));
        status.setName(statusDTO.getName());
        status.setColor(statusDTO.getColor());
        statusRepo.save(status);
        return status;
    }
}
