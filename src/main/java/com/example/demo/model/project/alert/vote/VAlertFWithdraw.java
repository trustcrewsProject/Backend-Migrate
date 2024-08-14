package com.example.demo.model.project.alert.vote;

import com.example.demo.constant.ProjectAlertType;
import com.example.demo.global.common.BaseTimeEntity;
import com.example.demo.model.projectVote.fwithdraw.VoteFWithdraw;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "project_alert_vote_fw")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class VAlertFWithdraw extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_alert_vote_fw_id")
    private Long id;

    private Long project_id;

    @OneToOne
    @JoinColumn(name = "voteFW_id")
    private VoteFWithdraw voteFWithdraw;

    private String alertContents;

    @Column(name = "project_alert_type")
    @Enumerated(value = EnumType.STRING)
    private ProjectAlertType alertType;

    @Builder
    public VAlertFWithdraw(Long project_id, VoteFWithdraw voteFWithdraw, String alertContents){
        this.project_id = project_id;
        this.voteFWithdraw = voteFWithdraw;
        this.alertContents = alertContents;
        this.alertType = ProjectAlertType.PRA1003;
    }



}
