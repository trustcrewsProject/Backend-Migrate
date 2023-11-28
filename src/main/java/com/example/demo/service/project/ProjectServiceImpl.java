package com.example.demo.service.project;

import com.example.demo.constant.AlertType;
import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.dto.position.response.PositionResponseDto;
import com.example.demo.dto.project.request.ProjectConfirmRequestDto;
import com.example.demo.dto.project.request.ProjectParticipateRequestDto;
import com.example.demo.dto.project.response.ProjectMeResponseDto;
import com.example.demo.dto.project.response.ProjectSpecificDetailResponseDto;
import com.example.demo.dto.projectmember.response.MyProjectMemberResponseDto;
import com.example.demo.dto.projectmember.response.ProjectMemberAuthResponseDto;
import com.example.demo.dto.projectmember.response.ProjectMemberDetailResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeResponseDto;
import com.example.demo.dto.user.response.UserMyProjectResponseDto;
import com.example.demo.dto.user.response.UserProjectDetailResponseDto;
import com.example.demo.dto.user.response.UserProjectResponseDto;
import com.example.demo.dto.work.response.WorkProjectDetailResponseDto;
import com.example.demo.global.exception.customexception.*;
import com.example.demo.model.alert.Alert;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.project.ProjectMemberAuth;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import com.example.demo.repository.alert.AlertRepository;
import com.example.demo.repository.position.PositionRepository;
import com.example.demo.repository.project.ProjectMemberAuthRepository;
import com.example.demo.repository.project.ProjectMemberRepository;
import com.example.demo.repository.project.ProjectRepository;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.repository.work.WorkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final WorkRepository workRepository;
    private final ProjectMemberAuthRepository projectMemberAuthRepository;
    private final PositionRepository positionRepository;
    private final AlertRepository alertRepository;

    public Project save(Project project){
        return projectRepository.save(project);
    }

    @Override
    public List<Project> findProjectsByUser(User user) {
        return projectRepository.findProjectsByUser(user).orElseThrow(() -> ProjectCustomException.NOT_FOUND_PROJECT);
    }

    public Project findById(Long id){
        return projectRepository
                .findById(id)
                .orElseThrow(() -> ProjectCustomException.NOT_FOUND_PROJECT);
    }





    /**
     * 참여하기 참여하는 경우 알림보내기
     * TODO : 지원자 아이디 jwt token으로 받기.
     * @param projectId
     * @param projectParticipateRequestDto
     */
    public void sendParticipateAlert(Long projectId, ProjectParticipateRequestDto projectParticipateRequestDto) {
        Project project = projectRepository
                        .findById(projectId)
                        .orElseThrow(() -> ProjectCustomException.NOT_FOUND_PROJECT);
        User user = userRepository.findById(1L).orElseThrow(() -> UserCustomException.NOT_FOUND_USER);

        Position position = positionRepository.findById(projectParticipateRequestDto.getPositionId()).orElseThrow(() -> PositionCustomException.NOT_FOUND_POSITION);

        Alert alert = Alert.builder()
                        .project(project)
                        .checkUser(project.getUser())
                        .applyUser(user)
                        .content("프로젝트 지원했습니다.")
                        .position(position)
                        .type(AlertType.RECRUIT)
                        .checked_YN(false)
                        .build();

        alertRepository.save(alert);
    }

    /**
     * 참여 수락하기
     * TODO : 사용자 jwt token으로 사용하기
     * @param projectId
     * @param projectConfirmRequestDto
     */
    public void confirm(Long projectId, ProjectConfirmRequestDto projectConfirmRequestDto) {
        Project project = projectRepository
                        .findById(projectId)
                        .orElseThrow(() -> ProjectCustomException.NOT_FOUND_PROJECT);

        User user = userRepository.findById(1L).orElseThrow(() -> UserCustomException.NOT_FOUND_USER);

        ProjectMemberAuth projectMemberAuth = projectMemberAuthRepository
                .findTopByOrderByIdDesc()
                .orElseThrow(() -> ProjectMemberAuthCustomException.NOT_FOUND_PROJECT_MEMBER_AUTH);

        Position position = positionRepository
                        .findById(projectConfirmRequestDto.getPositionId())
                        .orElseThrow(() -> PositionCustomException.NOT_FOUND_POSITION);

        ProjectMember projectMember =
                ProjectMember.builder()
                        .project(project)
                        .user(user)
                        .projectMemberAuth(projectMemberAuth)
                        .status(ProjectMemberStatus.PARTICIPATING)
                        .position(position)
                        .build();

        projectMemberRepository.save(projectMember);
    }

    /**
     * 프로젝트 종료하기
     *
     * @param projectId
     */
    public void end(Long projectId) {
        Project project = projectRepository
                        .findById(projectId)
                        .orElseThrow(() -> ProjectCustomException.NOT_FOUND_PROJECT);

        project.endProject();
    }
}
