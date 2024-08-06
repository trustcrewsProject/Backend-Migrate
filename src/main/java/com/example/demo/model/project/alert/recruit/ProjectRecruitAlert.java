package com.example.demo.model.project.alert.recruit;


import com.example.demo.global.common.BaseTimeEntity;
import com.example.demo.model.projectVote.ProjectVoteRecruit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "project_recruit_alert")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProjectRecruitAlert extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_recruit_alert_id")
    private Long id;

    private Long project_id;

    @OneToOne
    @JoinColumn(name = "project_vote_recruit_id")
    private ProjectVoteRecruit projectVoteRecruit;

    @Builder
    public ProjectRecruitAlert(Long project_id, ProjectVoteRecruit projectVoteRecruit){
        this.project_id = project_id;
        this.projectVoteRecruit = projectVoteRecruit;
    }

}
