package com.project.salemanagement.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="role")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="name",nullable = false)
    private String name;
    @Column(name="description",nullable = true)
    private String description;
    public static String ADMIN = "ADMIN";
    public static String USER = "USER";
}
