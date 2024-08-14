package com.example.demo.service.projectVote.recruit.history;

import com.example.demo.dto.projectVote.recruit.ProjectVoteRecruitRequestDto;
import com.example.demo.model.projectVote.recruit.history.VoteRecruitHistory;
import com.example.demo.repository.projectVote.recruit.history.VoteRecruitHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VoteRecruitHistoryServiceImpl implements VoteRecruitHistoryService {

    private final VoteRecruitHistoryRepository voteRecruitHistoryRepository;


    @Override
    public VoteRecruitHistory findProjectVoteRecruitHistoryByVoter(Long voteId, Long voterId) {
        return voteRecruitHistoryRepository.findProjectVoteRecruitHistoryByVoter(voteId, voterId);
    }

    @Override
    public VoteRecruitHistory createProjectVoteRecruitHistory(Long userId, ProjectVoteRecruitRequestDto requestDto) {
        VoteRecruitHistory voteRecruitHistory =
                VoteRecruitHistory.builder()
                        .project_vote_id(requestDto.getVoteId())
                        .voter_id(userId)
                        .answer(requestDto.getAnswer())
                        .build();

        return voteRecruitHistoryRepository.save(voteRecruitHistory);
    }
}
