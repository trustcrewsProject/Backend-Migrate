package com.example.demo.service.projectApply;

import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.projectApply.ProjectApplyRequestDto;
import com.example.demo.dto.projectApply.ProjectApplyResponseDto;
import com.example.demo.global.exception.customexception.PageNationCustomException;
import com.example.demo.model.project.alert.vote.VAlertRecruit;
import com.example.demo.model.projectApply.ProjectApply;
import com.example.demo.model.projectVote.recruit.VoteRecruit;
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

    public void projectApply(long userId, ProjectApplyRequestDto requestDto) {
        try{
            ProjectApply projectApply = projectApplyService.toProjectApplyEntity(
                    userId,
                    requestDto.getProjectId(),
                    requestDto.getPositionId(),
                    requestDto.getApply_message()
            );

            VoteRecruit voteRecruit = projectVoteServiceRecruit.createProjectVoteRecruit(projectApply);
            String positionName = voteRecruit.getProjectApply().getPosition().getName();
            String applierNickname = voteRecruit.getProjectApply().getUser().getNickname();
            String alertContents =  applierNickname + " 님이 " + positionName + " 포지션에 지원했습니다.";

            VAlertRecruit vAlertRecruit = vAlertRecruitService.toProjectRecruitAlertEntity(requestDto.getProjectId(), voteRecruit, alertContents);

            System.out.println("projectRecruitAlert:: "+ vAlertRecruit.getId());
        }catch(Exception e){
           e.printStackTrace();
        }

    }

    public PaginationResponseDto getMyApplyProjects(Long userId, int pageIndex, int itemCount){
        if (pageIndex < 0) {
            throw PageNationCustomException.INVALID_PAGE_NUMBER;
        }
        List<ProjectApplyResponseDto> projectApplies = new ArrayList<>();
        long totalItems = 0;

        try {
            totalItems = projectApplyService.countProjectAppliesByUserId(userId);
            if(totalItems > 0){
                Pageable pageable = PageRequest.of(pageIndex, itemCount);
                projectApplies = projectApplyService.findProjectAppliesByUserId(userId, pageable);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return PaginationResponseDto.of(projectApplies, totalItems);

    }

}
