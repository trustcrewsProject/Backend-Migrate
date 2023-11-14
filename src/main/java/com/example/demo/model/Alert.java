package com.example.demo.model;

import com.example.demo.constant.AlertType;
import javax.persistence.*;

import com.example.demo.constant.Role;
import lombok.*;

import java.util.List;

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
    private User user;

    private String content;

    private AlertType type;

    @Column(name = "checked_yn")
    private boolean checked_YN;

    @Builder
    private Alert(
            Project project,
            User user,
            String content,
            AlertType type,
            boolean checked_YN
    ) {
        this.project = project;
        this.user = user;
        this.content = content;
        this.type = type;
        this.checked_YN = checked_YN;
    }
}
