package com.project.salemanagement.repositories;

import com.project.salemanagement.models.DashBoard;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface DashBoardRepo  extends JpaRepository<DashBoard,Long> {
    List<DashBoard> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Sort sort);

}
