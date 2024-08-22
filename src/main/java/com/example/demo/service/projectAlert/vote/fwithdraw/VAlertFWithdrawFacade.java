package com.example.demo.service.projectAlert.vote.fwithdraw;

import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.projectAlert.vote.fwithdraw.VAlertFWCreateRequestDto;
import com.example.demo.dto.projectAlert.vote.fwithdraw.VAlertFWDetailResponseDto;
import com.example.demo.dto.projectAlert.vote.fwithdraw.VAlertFWResponseDto;
import com.example.demo.global.exception.customexception.PageNationCustomException;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.projectVote.fwithdraw.VoteFWithdraw;
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

    public void createFWithdrawAlert(Long userId, VAlertFWCreateRequestDto requestDto){
        ProjectMember fwMember = projectMemberService.findById(requestDto.getFw_member_id());

        // 투표 생성
        VoteFWithdraw voteFWithdraw = vfWithdrawService.toVoteFWithdrawEntity(userId, fwMember, requestDto.getReason(), requestDto.getProject_id());

        // 알림 생성
        String alertContents = fwMember.getUser().getNickname() + "님에 대한 강제탈퇴 투표 알림";
        vAlertFWithdrawService.toVAlertFWithdrawEntity(requestDto.getProject_id(), voteFWithdraw, alertContents);
    }

    // todo - 강제탈퇴 알림 목록, 강제탈퇴 알림 상세조회 api
    public PaginationResponseDto getVAlertFWithdrawList(Long projectId, int pageIndex, int itemCount){
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

    public VAlertFWDetailResponseDto getVAlertFWithdrawDetail(Long voteId, Long fwMemberId){
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
                projectMember.getProjectMemberAuth(),
                projectMember.getPosition(),
                projectMember.getUser().getProfileImgSrc(),
                projectMember.getUser().getNickname());
    }

}
