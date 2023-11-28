package com.example.demo.service.project;

import com.example.demo.model.alert.Alert;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.service.alert.AlertService;
import org.springframework.stereotype.Service;

@Service
public class ProjectMemberFacade {

    private ProjectMemberService projectMemberService;
    private AlertService alertService;

    /**
     * 프로젝트 멤버 탈퇴 알림 보내기
     *
     * @param projectMemberId
     */
    public void sendWithdrawlAlert(Long projectMemberId) {
        ProjectMember projectMember = projectMemberService.findById(projectMemberId);
        Alert alert = alertService.toAlertEntity(projectMember, projectMember.getPosition());
        alertService.save(alert);
    }
}
