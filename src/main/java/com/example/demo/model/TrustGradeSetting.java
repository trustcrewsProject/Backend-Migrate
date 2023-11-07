package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "trust_grade_setting")
public class TrustGradeSetting extends BaseTimeEntity{
    @Id
    @Column(name = "trust_grade_setting_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int trsutScore;
}
