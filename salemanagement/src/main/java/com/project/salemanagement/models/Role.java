package com.project.salemanagement.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name="role")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role extends BaseModel{

    @Column(name="name",nullable = false)
    private String name;
    @Column(name="description",nullable = true)
    private String description;
    public static String ADMIN = "ADMIN";
    public static String USER = "USER";
}
