package com.example.demo.service.projectApply;

import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.projectApply.ProjectApplyRequestDto;
import com.example.demo.dto.projectApply.ProjectApplyResponseDto;
import com.example.demo.global.exception.customexception.PageNationCustomException;
import com.example.demo.global.exception.customexception.ProjectPartiCustomException;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.projectApply.ProjectApply;
import com.example.demo.model.projectVote.recruit.VoteRecruit;
import com.example.demo.service.project.ProjectMemberService;
import com.example.demo.service.projectAlert.vote.recruit.VAlertRecruitService;
import com.example.demo.service.projectVote.recruit.VoteRecruitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectApplyFacade {

    private final ProjectApplyService projectApplyService;
    private final VoteRecruitService projectVoteServiceRecruit;
    private final VAlertRecruitService vAlertRecruitService;
    private final ProjectMemberService projectMemberService;

    public void projectApply(long userId, ProjectApplyRequestDto requestDto) {

        projectApplyValidate(requestDto.getProjectId(), userId);

        ProjectApply projectApply = projectApplyService.toProjectApplyEntity(
                userId,
                requestDto.getProjectId(),
                requestDto.getPositionId(),
                requestDto.getApply_message()
        );

        VoteRecruit voteRecruit = projectVoteServiceRecruit.createProjectVoteRecruit(projectApply);
        String positionName = voteRecruit.getProjectApply().getPosition().getName();
        String applierNickname = voteRecruit.getProjectApply().getUser().getNickname();
        String alertContents = applierNickname + " 님의 " + positionName + " 포지션 참여 투표 알림";

        vAlertRecruitService.toProjectRecruitAlertEntity(requestDto.getProjectId(), voteRecruit, alertContents);

    }

    public PaginationResponseDto getMyApplyProjects(Long userId, int pageIndex, int itemCount) {
        if (pageIndex < 0) {
            throw PageNationCustomException.INVALID_PAGE_NUMBER;
        }
        List<ProjectApplyResponseDto> projectApplies = new ArrayList<>();
        long totalItems = 0;

        try {
            totalItems = projectApplyService.countProjectAppliesByUserId(userId);
            if (totalItems > 0) {
                Pageable pageable = PageRequest.of(pageIndex, itemCount);
                projectApplies = projectApplyService.findProjectAppliesByUserId(userId, pageable);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return PaginationResponseDto.of(projectApplies, totalItems);

    }

    public void projectApplyValidate(Long projectId, Long userId) {

        ProjectMember thisProjectMember = projectMemberService.findProjectMemberByPrIdAndUserId(projectId, userId);

        if(thisProjectMember != null){
            // 강제탈퇴된 사용자 재참여 방지
            if(thisProjectMember.getStatus().equals(ProjectMemberStatus.FORCE_WITHDRAW)){
                throw ProjectPartiCustomException.PARTICIPATE_NOT_ALLOWED;
            }

            // 이미 참여중인 프로젝트 지원 방지
            if(thisProjectMember.getStatus().equals(ProjectMemberStatus.PARTICIPATING)){
                throw ProjectPartiCustomException.PARTICIPATE_EXIST;
            }
        }

        // 프로젝트 중복지원 방지
        long projectApplying = projectApplyService.countUserProjectApplying(projectId, userId);
        if (projectApplying > 0) {
            throw ProjectPartiCustomException.PARTICIPATE_DUPLICATE;
        }
    }

}
