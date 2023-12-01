package com.example.demo.model.user;

import com.example.demo.constant.Role;
import com.example.demo.global.common.BaseTimeEntity;
import com.example.demo.model.position.Position;
import com.example.demo.model.trust_grade.TrustGrade;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserTechnologyStack> techStacks;

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
            Role role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImgSrc = profileImgSrc;
        this.intro = intro;
        this.position = position;
        this.techStacks = techStacks;
        this.role = role;
    }

    // 기술스택 목록 등록
    public void setTechStacks(List<UserTechnologyStack> techStacks) {
        this.techStacks = techStacks;
    }
}
