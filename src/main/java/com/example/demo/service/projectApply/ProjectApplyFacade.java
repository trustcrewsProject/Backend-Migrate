package com.example.demo.service.projectApply;

import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.projectApply.ProjectApplyRequestDto;
import com.example.demo.dto.projectApply.ProjectApplyResponseDto;
import com.example.demo.global.exception.customexception.PageNationCustomException;
import com.example.demo.model.project.alert.recruit.ProjectRecruitAlert;
import com.example.demo.model.projectApply.ProjectApply;
import com.example.demo.model.projectVote.ProjectVoteRecruit;
import com.example.demo.service.projectAlert.recruit.ProjectAlertRecruitService;
import com.example.demo.service.projectVote.recruit.ProjectVoteServiceRecruit;
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

    private final ProjectVoteServiceRecruit projectVoteServiceRecruit;

    private final ProjectAlertRecruitService projectAlertRecruitService;

    public void projectApply(long userId, ProjectApplyRequestDto requestDto) {
        try{
            ProjectApply projectApply = projectApplyService.toProjectApplyEntity(
                    userId,
                    requestDto.getProjectId(),
                    requestDto.getPositionId(),
                    requestDto.getApply_message()
            );

            ProjectVoteRecruit projectVoteRecruit = projectVoteServiceRecruit.createProjectVoteRecruit(projectApply);

            ProjectRecruitAlert projectRecruitAlert = projectAlertRecruitService.toProjectRecruitAlertEntity(requestDto.getProjectId(), projectVoteRecruit);

            System.out.println("projectRecruitAlert:: "+ projectRecruitAlert.getId());
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
