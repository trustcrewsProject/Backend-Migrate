package com.example.demo.service.trust_score;

import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.repository.trust_score.TrustScoreHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrustScoreHistoryServiceImpl implements TrustScoreHistoryService {
    private final TrustScoreHistoryRepository trustScoreHistoryRepository;

    @Override
    public PaginationResponseDto getWorkTrustScoreHistories(Project project, User user, int pageIndex, int itemCount) {
        return trustScoreHistoryRepository
                .findByProjectAndUserAndWorkIsNotNullOrderByCreateDate(project.getId(), user.getId(), PageRequest.of(pageIndex, itemCount));
    }
}
