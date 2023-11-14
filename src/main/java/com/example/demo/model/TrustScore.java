package com.example.demo.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trust_score")
public class TrustScore extends BaseTimeEntity {
    @Id
    @Column(name = "trust_score_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private int score;
}
