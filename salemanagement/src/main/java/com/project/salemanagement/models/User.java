package com.project.salemanagement.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="user")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_"+ getRole().getName().toUpperCase()));
        return authorityList;
    }

    @Override
    public String getUsername() {
        return email;
    }

}
