package com.example.demo.model.trust_score;

import com.example.demo.global.common.BaseTimeEntity;
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
    private Long id;
    /**
     * 신뢰점수타입부 자동생성 식별자
     */
    @Column
    private Long upTrustScoreTypeId;
    /**
     * 신뢰점수타입명
     */
    @Column
    private String trustScoreTypeName;
    /**
     * 신뢰등급명
     */
    @Column
    private String trustGradeName;
    /**
     * 유저자동생성식별자
     */
    @Column
    private int score;
    /**
     * 유저자동생성식별자
     */
    @Column
    private String gubunCode;
}
