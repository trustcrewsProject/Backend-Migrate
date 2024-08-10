package com.example.demo.service.projectAlert.vote.recruit;

import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.position.response.PositionInfoResponseDto;
import com.example.demo.dto.projectAlert.recruit.ProjectRecruitAlertDetailResponseDto;
import com.example.demo.dto.projectAlert.recruit.ProjectRecruitAlertResponseDto;
import com.example.demo.dto.projectVote.recruit.ProjectVoteRecruitResponseDto;
import com.example.demo.dto.technology_stack.response.TechnologyStackInfoResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeInfoResponseDto;
import com.example.demo.dto.user.response.UserMyInfoResponseDto;
import com.example.demo.global.exception.customexception.PageNationCustomException;
import com.example.demo.model.position.Position;
import com.example.demo.model.projectApply.ProjectApply;
import com.example.demo.model.projectVote.recruit.VoteRecruit;
import com.example.demo.model.technology_stack.TechnologyStack;
import com.example.demo.model.trust_grade.TrustGrade;
import com.example.demo.model.trust_score.TrustScore;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserTechnologyStack;
import com.example.demo.repository.projectApply.ProjectApplyRepository;
import com.example.demo.repository.projectVote.VoteRecruitRepository;
import com.example.demo.service.user.UserProjectHistoryService;
import com.example.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class VAlertFacade {

    private final VAlertRecruitService vAlertRecruitService;
    private final UserService userService;
    private final UserProjectHistoryService userProjectHistoryService;
    private final ProjectApplyRepository projectApplyRepository;
    private final VoteRecruitRepository voteRecruitRepository;

    /**
     * 프로젝트 크루모집 알림 조회
     *
     * @param projectId
     * @param pageIndex
     * @param itemCount
     * @return
     */
    public PaginationResponseDto getProjectAlertRecruitList(Long projectId, int pageIndex, int itemCount) {
        if (pageIndex < 0) {
            throw PageNationCustomException.INVALID_PAGE_NUMBER;
        }

        List<ProjectRecruitAlertResponseDto> responseDtoList = new ArrayList<>();
        long totalItems = 0;

        try {
            totalItems = vAlertRecruitService.countProjectRecruitAlerts(projectId);
            if (totalItems > 0) {
                Pageable pageable = PageRequest.of(pageIndex, itemCount);
                responseDtoList = vAlertRecruitService.findProjectRecruitAlertLists(projectId, pageable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return PaginationResponseDto.of(responseDtoList, totalItems);
    }

    public Map<String, Object> getProjectAlertRecruitDetail(Long alertId, Long applyId, Long voteId) {

        Map<String, Object> result = new HashMap<>();

        // 모집 정보
        ProjectApply projectApply = projectApplyRepository.findProjectApplyById(applyId);

        User currentUser = projectApply.getUser();

        // 신뢰점수
        TrustScore trustScore = currentUser.getTrustScore();
        // 신뢰등급
        TrustGrade trustGrade = trustScore.getTrustGrade();
        // 포지션
        Position position = currentUser.getPosition();
        // 기술스택목록
        List<TechnologyStack> techStacks =
                currentUser.getTechStacks().stream()
                        .map(UserTechnologyStack::getTechnologyStack)
                        .collect(Collectors.toList());

        // 신뢰등급 정보 응답 DTO
        TrustGradeInfoResponseDto trustGradeInfo =
                TrustGradeInfoResponseDto.of(trustGrade.getId(), trustGrade.getName());
        // 포지션 정보 응답 DTO
        PositionInfoResponseDto positionInfo =
                PositionInfoResponseDto.of(position.getId(), position.getName());
        // 기술스택목록 정보 응답 DTO
        List<TechnologyStackInfoResponseDto> techStacksInfo =
                techStacks.stream()
                        .map(
                                technologyStack ->
                                        TechnologyStackInfoResponseDto.of(
                                                technologyStack.getId(), technologyStack.getName()))
                        .collect(Collectors.toList());
        // 회원 프로젝트 이력 개수
        long projectHistoryTotalCount =
                userProjectHistoryService.getUserProjectHistoryTotalCount(currentUser.getId(), null);

        // 내 정보 응답 DTO 생성
        UserMyInfoResponseDto myInfoResponse =
                UserMyInfoResponseDto.of(
                        currentUser.getId(),
                        currentUser.getEmail(),
                        currentUser.getNickname(),
                        currentUser.getProfileImgSrc(),
                        currentUser.getIntro(),
                        trustScore.getScore(),
                        trustGradeInfo,
                        positionInfo,
                        techStacksInfo,
                        projectHistoryTotalCount,
                        currentUser.getCreateDate(),
                        currentUser.getUpdateDate());

        result.put("applicantInfo", myInfoResponse);


        // 모집 투표 정보
        ProjectVoteRecruitResponseDto voteRecruit = voteRecruitRepository.findProjectVoteRecruitItem(voteId);
        result.put("voteInfo", voteRecruit);

        return result;
    }

}
