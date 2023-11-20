package com.example.demo.model.trust_score;

import com.example.demo.constant.ScoreTypeDistinguishCode;
import com.example.demo.global.common.BaseTimeEntity;
import com.example.demo.model.trust_grade.TrustGrade;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "trust_score_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TrustScoreType extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trust_score_type_id")
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "score")
    Long score;

    @Column(name = "distinguish_code")
    @Enumerated(EnumType.STRING)
    ScoreTypeDistinguishCode distinguishCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trust_grade_id")
    TrustGrade trustGrade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trust_point_type_parent_id")
    TrustScoreType trustPointTypeParent;
}
