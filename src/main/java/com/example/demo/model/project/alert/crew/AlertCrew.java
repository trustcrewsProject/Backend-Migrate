package com.example.demo.model.project.alert.crew;

import com.example.demo.constant.ProjectAlertType;
import com.example.demo.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "project_alert_crew")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AlertCrew extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_alert_crew_id")
    private Long id;
    private Long project_id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "project_alert_type")
    private ProjectAlertType projectAlertType;
    private String alertContents;

    @Builder
    public AlertCrew(Long project_id, String alertContents){
        this.project_id = project_id;
        this.projectAlertType = ProjectAlertType.PRA2001;
        this.alertContents = alertContents;
    }
}
