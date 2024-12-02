package com.project.salemanagement.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="user")
@Data
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="fullname",nullable = false)
    private String name;
    @Column(name="email",nullable = false)
    private String email;
    @Column(name="phone",nullable = false)
    private String phone;
    @Column(name="password",nullable = false)
    private String password;
    @Column(name="address")
    private String address;
    @Column(name="is_active")
    private String is_active;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
