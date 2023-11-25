package com.example.demo.service;

import com.example.demo.constant.AlertType;
import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.constant.ProjectStatus;
import com.example.demo.dto.ProjectMember.Response.MyProjectMemberResponseDto;
import com.example.demo.dto.ProjectMember.Response.ProjectMemberDetailResponseDto;
import com.example.demo.dto.User.Response.UserMyProjectResponseDto;
import com.example.demo.dto.User.Response.UserProjectDetailResponseDto;
import com.example.demo.dto.User.Response.UserProjectResponseDto;
import com.example.demo.dto.position.Response.PositionResponseDto;
import com.example.demo.dto.project.Request.ProjectConfirmRequestDto;
import com.example.demo.dto.project.Request.ProjectParticipateRequestDto;
import com.example.demo.dto.project.Response.ProjectMeResponseDto;
import com.example.demo.dto.project.Response.ProjectSpecificDetailResponseDto;
import com.example.demo.dto.project.response.ProjectMeResponseDto;
import com.example.demo.dto.projectmember.response.MyProjectMemberResponseDto;
import com.example.demo.dto.projectmemberauth.Response.ProjectMemberAuthResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeResponseDto;
import com.example.demo.dto.trustgrade.TrustGradeDto;
import com.example.demo.dto.user.response.UserMyProjectResponseDto;
import com.example.demo.dto.work.Response.WorkProjectDetailResponseDto;
import com.example.demo.global.exception.customexception.*;
import com.example.demo.model.*;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.user.User;
import com.example.demo.repository.*;
import java.util.ArrayList;
import java.util.List;

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

@Service
@AllArgsConstructor
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final WorkRepository workRepository;
    private final ProjectMemberAuthRepository projectMemberAuthRepository;
    private final PositionRepository positionRepository;
    private final AlertRepository alertRepository;

    /**
     * 내 프로젝트 목록 조회
     * TODO : 현재 유저 가져오기.
     * @return
     */

    @Transactional(readOnly = true)
    public List<ProjectMeResponseDto> getMyProjects() {
        User user = userRepository.findById(1L).orElseThrow(() -> UserCustomException.NOT_FOUND_USER);
        List<Project> projects = projectRepository.findByUser(user);
        List<ProjectMeResponseDto> result = new ArrayList<>();

        for (Project project : projects) {
            TrustGradeResponseDto trustGradeDto = TrustGradeResponseDto.of(project.getTrustGrade());

            List<MyProjectMemberResponseDto> myProjectMemberResponseDtos = new ArrayList<>();
            for (ProjectMember projectMember : project.getProjectMembers()) {
                UserMyProjectResponseDto userMyProjectResponseDto = UserMyProjectResponseDto.of(projectMember.getUser());
                MyProjectMemberResponseDto myProjectMemberResponseDto = MyProjectMemberResponseDto.of(projectMember, userMyProjectResponseDto);
                myProjectMemberResponseDtos.add(myProjectMemberResponseDto);
            }

            ProjectMeResponseDto projectMeResponseDto = ProjectMeResponseDto.of(project, trustGradeDto, myProjectMemberResponseDtos);
            result.add(projectMeResponseDto);
        }

        return result;
    }
}
