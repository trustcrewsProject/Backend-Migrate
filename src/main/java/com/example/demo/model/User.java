package com.example.demo.model;

import com.example.demo.constant.Role;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;

    private String password;
    private String address;
    private String address_detail;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String name, String email, String address, Role role, String password){
        this.name = name;
        this.email = email;
        this.address = address;
        this.role = role;
        this.password = password;
    }
}