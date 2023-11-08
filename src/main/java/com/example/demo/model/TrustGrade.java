package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "trust_grade")
public class TrustGrade extends BaseTimeEntity{
    @Id
    @Column(name = "trust_grade_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int score;

    public TrustGrade(TrustGradeRequestDto dto){
        this.name = dto.getName();
        this.score = dto.getScore();
    }
}
