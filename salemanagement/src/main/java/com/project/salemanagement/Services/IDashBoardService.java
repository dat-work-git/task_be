package com.project.salemanagement.Services;

import com.project.salemanagement.dtos.DashBoardDTO;
import com.project.salemanagement.models.DashBoard;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IDashBoardService {
    DashBoard createDashBoard(DashBoardDTO dashBoardDTO);
    List<DashBoard> getPostByDay(Date startDate, Date endDate);
}
