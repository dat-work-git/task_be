package com.project.salemanagement.dtos;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class TaskDTO {
    private String Title;
    private String description;
    private String action;
    private Boolean urgent;
    private String assign;
    private String status;
    private LocalDate startDate;
    private LocalDate completedDate;

}
