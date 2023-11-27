package com.example.demo.model.trust_score;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "trust_score")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TrustScore {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trust_score_id")
    private Long id;
    /**
     * 유저자동생성식별자
     */
    @Column
    private Long userId;

    /**
     * 유저신뢰점수값
     */
    @Column
    private int score;

    /**
     * 변경날짜
     */
    @Column
    private Date updateDate;
}
