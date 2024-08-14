package com.example.demo.service.projectVote.fwithdraw;

import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.constant.UserProjectHistoryStatus;
import com.example.demo.constant.VoteResult;
import com.example.demo.dto.projectVote.fwithdraw.VoteFWithdrawRequestDto;
import com.example.demo.dto.trust_score.AddPointDto;
import com.example.demo.global.exception.customexception.VoteCustomException;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.projectVote.fwithdraw.VoteFWithdraw;
import com.example.demo.model.projectVote.fwithdraw.history.VoteFWithdrawHistory;
import com.example.demo.model.trust_grade.TrustGrade;
import com.example.demo.model.trust_score.TrustScore;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserProjectHistory;
import com.example.demo.service.project.ProjectMemberService;
import com.example.demo.service.projectAlert.crew.AlertCrewService;
import com.example.demo.service.projectVote.fwithdraw.history.VFWithdrawHistoryService;
import com.example.demo.service.trust_score.TrustScoreService;
import com.example.demo.service.user.UserProjectHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VFWithdrawFacade {

    private final VFWithdrawHistoryService vfWithdrawHistoryService;
    private final VFWithdrawService vfWithdrawService;
    private final ProjectMemberService projectMemberService;
    private final UserProjectHistoryService userProjectHistoryService;
    private final TrustScoreService trustScoreService;
    private final AlertCrewService alertCrewService;

    public void voteForFWithdraw(Long userId, VoteFWithdrawRequestDto requestDto) {
        // 투표자 validation
        validateVoter(userId, requestDto);

        // 투표 (찬/반)
        VoteFWithdraw voteFWithdraw = vfWithdrawService.updateVoteDA(requestDto.getVoteId(), requestDto.getAnswer());

        VoteResult voteResult = vfWithdrawService.getProjectVoteResult(
                voteFWithdraw.getMax_vote_count(),
                voteFWithdraw.getAgrees(),
                voteFWithdraw.getDisagrees()
        );

        // 투표 기록 저장
        vfWithdrawHistoryService.toVoteFWithdrawHistoryEntity(voteFWithdraw.getId(), userId, requestDto.getAnswer());

        if (!voteResult.equals(VoteResult.NOT_FULFILLED)) {
            // 강제탈퇴 투표 종료
            vfWithdrawService.endProjectVote(voteFWithdraw.getId());

            ProjectMember fwMember = projectMemberService.findById(requestDto.getFw_member_id());
            if (voteResult.equals(VoteResult.AGREE)) { // 강제탈퇴 찬성

                User fwMemberUserInfo = fwMember.getUser();
                Project project = fwMember.getProject();

                // 강제탈퇴자 프로젝트 이력에 강제탈퇴 이력 추가
                UserProjectHistory forcedWithdrawalHistory = UserProjectHistory.builder()
                        .project(project)
                        .user(fwMemberUserInfo)
                        .status(UserProjectHistoryStatus.FORCED_WITHDRAWAL)
                        .build();
                userProjectHistoryService.save(forcedWithdrawalHistory);

                // 강제탈퇴에 따른 신뢰점수 차감
                TrustScore fwMemberTrustScore = trustScoreService.findTrustScoreByUserId(fwMemberUserInfo.getId());
                TrustGrade fwMemberTrustGrade = fwMemberTrustScore.getTrustGrade();

                AddPointDto addPoint = AddPointDto.builder()
                        .content(fwMemberUserInfo.getNickname() + "님 " + project.getName() + " 강제탈퇴")
                        .userId(fwMemberUserInfo.getId())
                        .projectId(project.getId())
                        .scoreTypeId(5L)
                        .build();
                trustScoreService.addPointOnUserTrustGrade(fwMemberTrustGrade, addPoint);

                // 멤버 탈퇴상태로 변경
                fwMember.updateStatus(ProjectMemberStatus.FORCE_WITHDRAW);

                // 강제탈퇴 알림 생성
                String alertContents = fwMemberUserInfo.getNickname() + "님이 강제 탈퇴 처리되었습니다.";
                alertCrewService.toAlertCrewEntity(project.getId(), alertContents);
            }
        }
    }

    public void validateVoter(Long userId, VoteFWithdrawRequestDto requestDto) {
        // 투표자 멤버 추가일자 검사
//        ProjectMember currentProjectMember = projectMemberService.getProjectMemberByPrIdAndUserId(requestDto.getProjectId(), userId);
//        LocalDateTime currentPMCreatedDate = currentProjectMember.getCreateDate();
//        LocalDateTime now = LocalDateTime.now();
//        long daysAfterCurrentMemberCreated = ChronoUnit.DAYS.between(currentPMCreatedDate, now);
//        if (daysAfterCurrentMemberCreated <= 3) {
//            throw VoteCustomException.VOTE_NOT_ALLOWED_YET;
//        }
//        if(userId.equals(requestDto.))

        // 투표대상자의 투표금지
        Long fwMemberId = requestDto.getFw_member_id();
        ProjectMember fwMember = projectMemberService.findById(fwMemberId);
        if (fwMember.getUser().getId().equals(userId)) {
            throw VoteCustomException.VOTE_NOT_ALLOWED;
        }

        // 투표 중복 검사
        VoteFWithdrawHistory voteFWithdrawHistory = vfWithdrawHistoryService.findVFWHistoryByVoter(requestDto.getVoteId(), userId);
        if (voteFWithdrawHistory != null) {
            throw VoteCustomException.VOTE_DUPLICATE;
        }

        // 투표 권한 검사
        if (!requestDto.getAuthMap().isVoteAuth()) {
            throw VoteCustomException.VOTE_NOT_ALLOWED;
        }
    }
}
