package com.example.demo.model.alert;

import com.example.demo.constant.AlertType;
import com.example.demo.global.common.BaseTimeEntity;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import javax.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "alert")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Alert extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "alert_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User checkUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User applyUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    @Nullable
    private Position position;

    private String content;

    private AlertType type;

    @Column(name = "checked_yn")
    private boolean checked_YN;

    @Builder
    private Alert(
            Project project,
            User checkUser,
            User applyUser,
            Position position,
            String content,
            AlertType type,
            boolean checked_YN) {
        this.project = project;
        this.checkUser = checkUser;
        this.applyUser = applyUser;
        this.position = position;
        this.content = content;
        this.type = type;
        this.checked_YN = checked_YN;
    }
}
