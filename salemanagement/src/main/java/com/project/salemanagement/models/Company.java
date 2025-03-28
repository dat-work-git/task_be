package com.project.salemanagement.models;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name="company")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company extends BaseModel{

    @Column(name="company_name",nullable = false)
    private String companyName;
    @Column(name="email",nullable = false)
    private String email;
    @Column(name="phone",nullable = false)
    private String phone;
    @ManyToOne
    @JoinColumn(name = "assigned_person", referencedColumnName = "email", nullable = false)
    private User assignedPerson;


}
