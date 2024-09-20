package com.example.demo.service.milestone;

import com.example.demo.constant.CreateLimitCnt;
import com.example.demo.constant.ProjectMemberAuth;
import com.example.demo.dto.milestone.request.DeleteMilestoneReqDto;
import com.example.demo.dto.milestone.request.MilestoneCreateRequestDto;
import com.example.demo.dto.milestone.response.MilestoneCreateResponseDto;
import com.example.demo.dto.milestone.response.MilestoneReadResponseDto;
import com.example.demo.global.exception.customexception.MilestoneCustomException;
import com.example.demo.global.exception.customexception.ProjectCustomException;
import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.service.project.ProjectMemberService;
import com.example.demo.service.project.ProjectService;
import com.example.demo.service.work.WorkService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class MileStoneFacade {
    private final MilestoneService mileStoneService;
    private final ProjectService projectService;
    private final ProjectMemberService projectMemberService;
    private final WorkService workService;

    /**
     * @param requestDto
     * @return
     */
    public MilestoneCreateResponseDto create(MilestoneCreateRequestDto requestDto) {

        int count = mileStoneService.countByProjectId(requestDto.getProjectId());
        if(count >= CreateLimitCnt.MILESTONE.getCount()){
            throw MilestoneCustomException.CREATE_EXCEEDED_MS;
        }

        validateMilestoneAuth(requestDto.getAuthMap());
        Project project = projectService.findById(requestDto.getProjectId());
        Milestone milestone = requestDto.toMileStoneEntity(project);
        Milestone savedMilestone = mileStoneService.save(milestone);

        return MilestoneCreateResponseDto.of(savedMilestone);
    }

    @Transactional(readOnly = true)
    public List<MilestoneReadResponseDto> getAllByProject(Long projectId, Long userId) {

        validateProjectMember(userId, projectId);

        Project project = projectService.findById(projectId);
        List<Milestone> milestonesByProject = mileStoneService.findMilestonesByProject(project);

        List<MilestoneReadResponseDto> milestoneReadResponseDtos = new ArrayList<>();
        for (Milestone milestone : milestonesByProject) {
            MilestoneReadResponseDto milestoneReadResponseDto = MilestoneReadResponseDto.of(milestone);
            milestoneReadResponseDtos.add(milestoneReadResponseDto);
        }

        return milestoneReadResponseDtos;
    }

    @Transactional
    public void deleteMilestone(Long userId, DeleteMilestoneReqDto reqDto) {
        validateMilestoneAuth(reqDto.getAuthMap());
        validateProjectMember(userId, reqDto.getProjectId());

        workService.deleteAllByMilestoneId(reqDto.getMilestoneId());
        mileStoneService.delete(reqDto.getMilestoneId());
    }

    public void validateProjectMember(Long userId, Long projectId) {
        ProjectMember projectMember = projectMemberService.findProjectMemberByPrIdAndUserId(projectId, userId);
        if (projectMember == null) {
            throw ProjectCustomException.ACCESS_NOT_ALLOWED;
        }
    }

    public void validateMilestoneAuth(ProjectMemberAuth authDto) {
        if (authDto == null || !authDto.isMilestoneChangeYN()) {
            throw ProjectCustomException.NO_PERMISSION_TO_TASK;
        }
    }

}
