package com.example.demo.service.projectAlert.vote.fwithdraw;

import com.example.demo.constant.ProjectMemberAuth;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.projectAlert.vote.fwithdraw.VAlertFWCreateRequestDto;
import com.example.demo.dto.projectAlert.vote.fwithdraw.VAlertFWDetailResponseDto;
import com.example.demo.dto.projectAlert.vote.fwithdraw.VAlertFWResponseDto;
import com.example.demo.dto.projectmember.ProjectMemberAuthDto;
import com.example.demo.global.exception.customexception.PageNationCustomException;
import com.example.demo.global.exception.customexception.ProjectCustomException;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.projectVote.fwithdraw.VoteFWithdraw;
import com.example.demo.service.file.AwsS3FileService;
import com.example.demo.service.project.ProjectMemberService;
import com.example.demo.service.projectVote.fwithdraw.VFWithdrawService;
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
public class VAlertFWithdrawFacade {
    private final VFWithdrawService vfWithdrawService;

    private final VAlertFWithdrawService vAlertFWithdrawService;

    private final ProjectMemberService projectMemberService;
    private final AwsS3FileService awsS3FileService;

    public void createFWithdrawAlert(Long userId, VAlertFWCreateRequestDto requestDto) {
        validateProjectMember(userId, requestDto.getProject_id());

        if (requestDto.getAuthMap() == null ||
                (requestDto.getFw_member_auth().equals(ProjectMemberAuth.PAUTH_1001)
                        && requestDto.getAuthMap().equals(ProjectMemberAuth.PAUTH_2001))
        ) {
            throw ProjectCustomException.NO_PERMISSION_TO_TASK;
        }

        ProjectMember fwMember = projectMemberService.findById(requestDto.getFw_member_id());

        // 투표 생성
        VoteFWithdraw voteFWithdraw = vfWithdrawService.toVoteFWithdrawEntity(userId, fwMember, requestDto.getReason(), requestDto.getProject_id());

        // 알림 생성
        String alertContents = fwMember.getUser().getNickname() + "님에 대한 강제탈퇴 투표 알림";
        vAlertFWithdrawService.toVAlertFWithdrawEntity(requestDto.getProject_id(), voteFWithdraw, alertContents);
    }

    public PaginationResponseDto getVAlertFWithdrawList(Long projectId, int pageIndex, int itemCount) {
        if (pageIndex < 0) {
            throw PageNationCustomException.INVALID_PAGE_NUMBER;
        }

        List<VAlertFWResponseDto> responseDtoList = new ArrayList<>();
        long totalItems = 0;

        try {
            totalItems = vAlertFWithdrawService.countVAlertFWList(projectId);
            if (totalItems > 0) {
                Pageable pageable = PageRequest.of(pageIndex, itemCount);
                responseDtoList = vAlertFWithdrawService.findVAlertFWList(projectId, pageable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return PaginationResponseDto.of(responseDtoList, totalItems);
    }

    public VAlertFWDetailResponseDto getVAlertFWithdrawDetail(Long voteId, Long fwMemberId) {
        // vote
        VoteFWithdraw voteFWithdraw = vfWithdrawService.findVFWById(voteId);

        // propject member
        ProjectMember projectMember = projectMemberService.findById(fwMemberId);

        return VAlertFWDetailResponseDto.of(
                voteFWithdraw.getId(),
                voteFWithdraw.getReason().getName(),
                voteFWithdraw.getReason().getCode(),
                voteFWithdraw.getAgrees(),
                voteFWithdraw.getDisagrees(),
                voteFWithdraw.getMax_vote_count(),
                voteFWithdraw.getVoteStatus(),
                ProjectMemberAuthDto.of(projectMember.getProjectMemberAuth()),
                projectMember.getPosition(),
                awsS3FileService.generatePreSignedUrl(projectMember.getUser().getProfileImgSrc()),
                projectMember.getUser().getNickname());
    }

    public void validateProjectMember(Long userId, Long projectId) {
        ProjectMember projectMember = projectMemberService.findProjectMemberByPrIdAndUserId(projectId, userId);
        if (projectMember == null) {
            throw ProjectCustomException.ACCESS_NOT_ALLOWED;
        }
    }

}
