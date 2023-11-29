package com.example.demo.service.project;

import com.example.demo.dto.position.response.PositionResponseDto;
import com.example.demo.dto.project.response.ProjectMemberReadCrewDetailResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeResponseDto;
import com.example.demo.model.alert.Alert;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.service.alert.AlertService;
import com.example.demo.dto.user.response.UserCrewDetailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectMemberFacade {

    private final ProjectMemberService projectMemberService;
    private final AlertService alertService;
    private final ProjectService projectService;

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

    /**
     * 크루정보 상세 페이지
     * 유저 정보들, 유저 기술들, 프로젝트 개수, 신뢰점수 이력들
     * @param projectMemberId
     */
    public ProjectMemberReadCrewDetailResponseDto getCrewDetail(Long projectMemberId){
        ProjectMember projectMember = projectMemberService.findById(projectMemberId);

        int projectCount = projectService.countProjectsByUser(projectMember.getUser());
        TrustGradeResponseDto trustGradeResponseDto = TrustGradeResponseDto.of(projectMember.getProject().getTrustGrade());
        PositionResponseDto positionResponseDto = PositionResponseDto.of(projectMember.getPosition());
        UserCrewDetailResponseDto userCrewDetailResponseDto = UserCrewDetailResponseDto.of(projectMember.getUser(),positionResponseDto, trustGradeResponseDto);

        return ProjectMemberReadCrewDetailResponseDto.of(
                projectMember,
                projectCount,
                userCrewDetailResponseDto,
                positionResponseDto
        );
    }
}
