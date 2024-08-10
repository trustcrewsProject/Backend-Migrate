package com.example.demo.service.projectVote.recruit.history;

import com.example.demo.dto.projectVote.recruit.ProjectVoteRecruitRequestDto;
import com.example.demo.model.projectVote.recruit.history.VoteRecruitHistory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface VoteRecruitHistoryService {
    VoteRecruitHistory findProjectVoteRecruitHistoryByVoter(Long voteId, Long voterId);

    VoteRecruitHistory createProjectVoteRecruitHistory(Long userId, ProjectVoteRecruitRequestDto requestDto);
}
