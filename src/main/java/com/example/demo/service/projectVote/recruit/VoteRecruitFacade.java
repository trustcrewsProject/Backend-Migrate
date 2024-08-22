package com.example.demo.service.projectVote.recruit;

import com.example.demo.constant.ProjectApplyStatus;
import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.constant.UserProjectHistoryStatus;
import com.example.demo.constant.VoteResult;
import com.example.demo.dto.projectVote.recruit.ProjectVoteRecruitRequestDto;
import com.example.demo.global.exception.customexception.VoteCustomException;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.project.ProjectMemberAuth;
import com.example.demo.model.projectApply.ProjectApply;
import com.example.demo.model.projectVote.recruit.VoteRecruit;
import com.example.demo.model.projectVote.recruit.history.VoteRecruitHistory;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserProjectHistory;
import com.example.demo.repository.projectApply.ProjectApplyRepository;
import com.example.demo.service.project.ProjectMemberAuthService;
import com.example.demo.service.project.ProjectMemberService;
import com.example.demo.service.projectAlert.crew.AlertCrewService;
import com.example.demo.service.projectApply.ProjectApplyService;
import com.example.demo.service.projectVote.recruit.history.VoteRecruitHistoryService;
import com.example.demo.service.user.UserProjectHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VoteRecruitFacade {
    private final VoteRecruitService voteRecruitService;
    private final VoteRecruitHistoryService voteRecruitHistoryService;
    private final ProjectMemberService projectMemberService;
    private final ProjectMemberAuthService projectMemberAuthService;
    private final ProjectApplyService projectApplyService;
    private final ProjectApplyRepository projectApplyRepository;
    private final AlertCrewService alertCrewService;
    private final UserProjectHistoryService userProjectHistoryService;

    public void voteForProjectRecruit(Long userId, ProjectVoteRecruitRequestDto requestDto) {

        // 투표자 validation
        validateVoter(userId, requestDto);

        // 투표 진행
        VoteRecruit voteRecruit = voteRecruitService.updateVoteRecruitDA(requestDto.getVoteId(), requestDto.getAnswer());

        // 투표결과 생성
        VoteResult voteResult = voteRecruitService.getProjectVoteResult(
                voteRecruit.getMax_vote_count(),
                voteRecruit.getAgrees(),
                voteRecruit.getDisagrees()
        );

        // 투표 이력 생성
        voteRecruitHistoryService.createProjectVoteRecruitHistory(userId, requestDto);

        if (!voteResult.equals(VoteResult.NOT_FULFILLED)) { // 모집투표 종료
            voteRecruitService.endProjectVote(requestDto.getVoteId());

            // 투표와 관련된 프로젝트 지원정보 조회
            ProjectApply projectApply = projectApplyRepository.findProjectApplyById(requestDto.getApplyId());
            if (voteResult.equals(VoteResult.AGREE)) { // 찬성
                User projectApplyUser = projectApply.getUser();
                Position projectApplyPosition = projectApply.getPosition();
                Project project = projectApply.getProject();
                ProjectMemberAuth projectMemberAuth = projectMemberAuthService.findProjectMemberAuthById(3L);

                ProjectMember projectMember = // 프로젝트 멤버 추가
                        projectMemberService.toProjectMemberEntity(
                                project,
                                projectApplyUser,
                                projectMemberAuth,
                                ProjectMemberStatus.PARTICIPATING,
                                projectApplyPosition
                        );
                projectMemberService.save(projectMember);

                // 사용자 프로젝트 이력 저장
                UserProjectHistory userProjectHistory =UserProjectHistory.builder()
                        .project(project)
                        .user(projectApplyUser)
                        .status(UserProjectHistoryStatus.PARTICIPATING)
                        .build();
                userProjectHistoryService.save(userProjectHistory);

                projectApplyService.udpateProjectApplyStatus( // 프로젝트 지원 상태 '수락'으로 변경
                        projectApply.getId(), ProjectApplyStatus.PAS1002
                );

                // 크루 알림 생성
                String alertContents = projectApplyUser.getNickname() + "님이 "
                        + projectApplyPosition.getName() + " 포지션으로 합류했습니다!";

                alertCrewService.toAlertCrewEntity(project.getId(), alertContents);

            } else { // 반대 - 프로젝트 지원상태 '거절'로 변경
                projectApplyService.udpateProjectApplyStatus(
                        projectApply.getId(), ProjectApplyStatus.PAS1003
                );
            }
        }

    }

    public void validateVoter(Long userId, ProjectVoteRecruitRequestDto requestDto) {
        // 투표 중복 검사
        VoteRecruitHistory voteRecruitHistory = voteRecruitHistoryService
                .findProjectVoteRecruitHistoryByVoter(requestDto.getVoteId(), userId);

        if (voteRecruitHistory != null) {
            throw VoteCustomException.VOTE_DUPLICATE;
        }

        // 투표 권한 검사
        if (!requestDto.getAuthMap().isVoteAuth()) {
            throw VoteCustomException.VOTE_NOT_ALLOWED;
        }
    }
}

