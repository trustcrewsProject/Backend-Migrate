package com.example.demo.model;

import com.example.demo.constant.ScoreTypeDistinguishCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trust_score_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TrustScoreType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trust_score_type_id")
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "score")
    Long score;

    @Column(name = "distinguish_code")
    ScoreTypeDistinguishCode distinguishCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trust_grade_id")
    TrustGrade trustGrade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trust_point_type_parent_id")
    TrustScoreType trustPointTypeParent;

    @Column(name = "create_date")
    LocalDateTime createDate;

    @Column(name = "update_date")
    LocalDateTime updateDate;
}
