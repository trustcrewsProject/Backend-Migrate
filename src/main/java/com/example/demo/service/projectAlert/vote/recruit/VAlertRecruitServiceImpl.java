package com.example.demo.service.projectAlert.vote.recruit;

import com.example.demo.dto.projectAlert.recruit.ProjectRecruitAlertDetailResponseDto;
import com.example.demo.dto.projectAlert.recruit.ProjectRecruitAlertResponseDto;
import com.example.demo.model.project.alert.vote.VAlertRecruit;
import com.example.demo.model.projectVote.recruit.VoteRecruit;
import com.example.demo.repository.projectAlert.vote.recruit.VAlertRecruitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VAlertRecruitServiceImpl implements VAlertRecruitService {
    private final VAlertRecruitRepository alertVoteRecruitRepository;

    @Override
    public VAlertRecruit toProjectRecruitAlertEntity(Long project_id, VoteRecruit voteRecruit, String alertContents) {
        VAlertRecruit vAlertRecruit = VAlertRecruit.builder()
                .project_id(project_id)
                .voteRecruit(voteRecruit)
                .alertContents(alertContents)
                .build();

        return alertVoteRecruitRepository.save(vAlertRecruit);

    }

    @Override
    public Long countProjectRecruitAlerts(Long projectId) {
        return alertVoteRecruitRepository.countProjectRecruitAlerts(projectId);
    }

    @Override
    public List<ProjectRecruitAlertResponseDto> findProjectRecruitAlertLists(Long projectId, Pageable pageable) {
        return alertVoteRecruitRepository.findProjectRecruitAlertList(projectId, pageable);
    }

//    @Override
//    public ProjectRecruitAlertDetailResponseDto findProjectRecruitAlertDetail(Long alertId) {
//        return alertVoteRecruitRepository.findProjectRecruitAlertDetail(alertId);
//    }
}
