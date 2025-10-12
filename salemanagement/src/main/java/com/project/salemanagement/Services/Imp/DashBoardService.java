package com.project.salemanagement.Services.Imp;

import com.project.salemanagement.repositories.DashBoardRepo;
import com.project.salemanagement.repositories.UserRepo;
import com.project.salemanagement.Services.IDashBoardService;
import com.project.salemanagement.dtos.DashBoardDTO;
import com.project.salemanagement.models.DashBoard;
import com.project.salemanagement.models.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class DashBoardService implements IDashBoardService {
    private final DashBoardRepo dashBoardRepo;
    private final UserRepo userRepo;

    @Override
    public DashBoard createDashBoard(DashBoardDTO dashBoardDTO) {
        User user = userRepo.findByEmail(dashBoardDTO.getEmailUser()).orElseThrow(
                () -> new EntityNotFoundException("Not found user")
        );
        DashBoard dashBoard = DashBoard.builder()
                .title(dashBoardDTO.getTitle())
                .description(dashBoardDTO.getDescription())
                .emailUser(user)
                .build();
        dashBoardRepo.save(dashBoard);
        return dashBoard;
    }

    @Override
    public List<DashBoard> getPostByDay(Date dateFrom, Date dateTo) {
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        LocalDateTime endDateTime = dateTo.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        endDateTime = endDateTime.with(LocalTime.MAX);
        LocalDateTime startDateTime = dateFrom.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return dashBoardRepo.findByCreatedAtBetween(startDateTime, endDateTime, sort);
    }


}
