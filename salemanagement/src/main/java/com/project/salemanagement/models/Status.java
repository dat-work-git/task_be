package com.project.salemanagement.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Status")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
}
