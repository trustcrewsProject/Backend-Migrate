package com.example.demo.model.trust_grade;

import com.example.demo.global.common.BaseTimeEntity;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "trust_grade")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TrustGrade extends BaseTimeEntity {
    @Id
    @Column(name = "trust_grade_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private int score;
}
