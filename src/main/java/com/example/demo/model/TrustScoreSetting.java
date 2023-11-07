package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "trust_score_setting")
public class TrustScoreSetting extends BaseTimeEntity{
    @Id
    @Column(name = "trust_score_setting_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private int score;
}
