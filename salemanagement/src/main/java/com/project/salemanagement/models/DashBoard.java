package com.project.salemanagement.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dashboard")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashBoard extends BaseModel{
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "email_user", nullable = false)
    private User emailUser;
}
