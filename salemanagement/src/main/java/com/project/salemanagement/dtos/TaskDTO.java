package com.project.salemanagement.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@Builder
public class TaskDTO {
    private String title;
    private String description;
    private String action;
    private Boolean urgent;
    private List<String> assignedUsers;
    private Long status;
    private LocalDate startDate;
    private LocalDate completedDate;
    private Long companyId;
}
