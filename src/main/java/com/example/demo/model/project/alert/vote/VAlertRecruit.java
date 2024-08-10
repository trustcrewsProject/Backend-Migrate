package com.example.demo.model.project.alert.vote;


import com.example.demo.constant.ProjectAlertType;
import com.example.demo.global.common.BaseTimeEntity;
import com.example.demo.model.projectApply.ProjectApply;
import com.example.demo.model.projectVote.recruit.VoteRecruit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "project_alert_voteRecruit")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class VAlertRecruit extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_alert_voteRecruit_id")
    private Long id;

    private Long project_id;

    @OneToOne
    @JoinColumn(name = "voteRecruit_id")
    private VoteRecruit voteRecruit;

    private String alertContents;

    @Column(name = "project_alert_type")
    @Enumerated(value = EnumType.STRING)
    private ProjectAlertType alertType;

    @Builder
    public VAlertRecruit(Long project_id, VoteRecruit voteRecruit, String alertContents){
        this.project_id = project_id;
        this.voteRecruit = voteRecruit;
        this.alertContents = alertContents;
        this.alertType = ProjectAlertType.PRA1002;
    }

}
