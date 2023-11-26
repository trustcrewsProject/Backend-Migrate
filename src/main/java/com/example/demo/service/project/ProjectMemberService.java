package com.example.demo.service.project;

import com.example.demo.constant.AlertType;
import com.example.demo.global.exception.customexception.ProjectMemberCustomException;
import com.example.demo.model.alert.Alert;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.repository.alert.AlertRepository;
import com.example.demo.repository.project.ProjectMemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectMemberService {
    private final ProjectMemberRepository projectMemberRepository;
    private final AlertRepository alertRepository;

    /**
     * 프로젝트 멤버 탈퇴 알림 보내기
     *
     * @param projectMemberId
     */
    public void sendWithdrawlAlert(Long projectMemberId) {
        ProjectMember projectMember =
                projectMemberRepository
                        .findById(projectMemberId)
                        .orElseThrow(() -> ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER);

        Alert alert =
                Alert.builder()
                        .project(projectMember.getProject())
                        .checkUser(projectMember.getProject().getUser())
                        .applyUser(projectMember.getUser())
                        .content("프로젝트 탈퇴")
                        .type(AlertType.WITHDRWAL)
                        .checked_YN(false)
                        .build();

        alertRepository.save(alert);
    }

    /**
     * 프로젝트 멤버 탈퇴 수락하기
     *
     * @param projectMemberId
     */
    public void withdrawlConfirm(Long projectMemberId) {
        ProjectMember projectMember =
                projectMemberRepository
                        .findById(projectMemberId)
                        .orElseThrow(() -> ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER);
        projectMemberRepository.delete(projectMember);
    }
}
