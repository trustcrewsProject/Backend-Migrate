package com.example.demo.service.projectVote.fwithdraw;

import com.example.demo.constant.ProjectFWReason;
import com.example.demo.constant.VoteOptionDA;
import com.example.demo.constant.VoteResult;
import com.example.demo.constant.VoteStatus;
import com.example.demo.global.exception.customexception.VoteCustomException;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.projectVote.fwithdraw.VoteFWithdraw;
import com.example.demo.repository.project.ProjectMemberRepository;
import com.example.demo.repository.projectVote.fwithdraw.VFWithdrawRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class VFWithdrawServiceImpl implements VFWithdrawService {

    private final VFWithdrawRepository vfWithdrawRepository;

    private final ProjectMemberRepository projectMemberRepository;

    @Override
    public VoteFWithdraw toVoteFWithdrawEntity(Long userId, ProjectMember fwMember, ProjectFWReason reason, Long projectId) {
        // 강제탈퇴 투표 대상자는 투표 방지
        if(Objects.equals(userId, fwMember.getUser().getId())){
            throw VoteCustomException.VOTE_NOT_ALLOWED;
        }

        int max_vote_count = projectMemberRepository.countFWVotableProjectMember(fwMember.getId(), projectId);
        // 투표가능인원 2명 이하일경우 예외 발생
        if(max_vote_count < 2) {
            throw VoteCustomException.VOTE_INSUFF_VOTERS;
        }

        // 진행중인 강제탈퇴 투표가 있으면 예외 발생
        VoteFWithdraw processingVote = vfWithdrawRepository.findProcessingVFWByProjectId(fwMember.getProject().getId());
        if (processingVote != null) {
            throw VoteCustomException.VOTE_EXIST_FW;
        }

        VoteFWithdraw voteFWithdraw = VoteFWithdraw.builder()
                .fwMember(fwMember)
                .max_vote_count(max_vote_count)
                .reason(reason)
                .project_id(projectId)
                .build();
        return vfWithdrawRepository.save(voteFWithdraw);
    }

    @Override
    public VoteFWithdraw updateVoteDA(Long voteId, VoteOptionDA answer) {
        return vfWithdrawRepository.updateVoteDA(voteId, answer);
    }

    @Override
    public VoteResult getProjectVoteResult(int maxVoteCount, int agrees, int disagrees) {
        int maxCountForOne = Math.round(maxVoteCount / 2);
        boolean isFulfilled =
                agrees + disagrees == maxVoteCount || agrees > maxCountForOne || disagrees > maxCountForOne;
        if (!isFulfilled) {
            return VoteResult.NOT_FULFILLED;
        }
        return agrees > maxCountForOne ? VoteResult.AGREE : VoteResult.DISAGREE;
    }

    @Override
    public void endProjectVote(Long voteId) {
        vfWithdrawRepository.updateVoteStatus(voteId, VoteStatus.VSTAT1002);
    }

    @Override
    public VoteFWithdraw findVFWById(Long voteId) {
        return vfWithdrawRepository.findVFWById(voteId);
    }

}
