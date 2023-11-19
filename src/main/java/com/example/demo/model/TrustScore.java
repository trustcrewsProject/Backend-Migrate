package com.example.demo.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trust_score")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TrustScore {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trust_score_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "trust_score")
    private Long trustScore;

    @Column(name = "update_date")
    private LocalDateTime updateDate;
}
