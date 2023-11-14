package com.example.demo.model;

import com.example.demo.constant.Role;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseTimeEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private String password;

    private String nickname;

    private String profileImgSrc;

    private String intro;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @OneToMany(fetch = FetchType.LAZY)
    private List<TechnologyStack> techStacks;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trust_grade_id")
    private TrustGrade trustGrade;

    private int trustScore;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    private User(
            String email,
            String password,
            String nickname,
            String profileImgSrc,
            String intro,
            Position position,
            List<TechnologyStack> techStacks,
            TrustGrade trustGrade,
            int trustScore,
            Role role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImgSrc = profileImgSrc;
        this.intro = intro;
        this.position = position;
        this.techStacks = techStacks;
        this.trustGrade = trustGrade;
        this.trustScore = trustScore;
        this.role = role;
    }
}
