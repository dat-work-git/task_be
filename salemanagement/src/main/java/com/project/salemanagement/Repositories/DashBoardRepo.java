package com.project.salemanagement.Repositories;

import com.project.salemanagement.models.DashBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DashBoardRepo  extends JpaRepository<DashBoard,Long> {
}
