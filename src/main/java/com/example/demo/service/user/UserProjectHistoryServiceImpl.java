package com.example.demo.service.user;

import com.example.demo.constant.UserProjectHistoryStatus;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserProjectHistory;
import com.example.demo.repository.user.UserProjectHistoryRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProjectHistoryServiceImpl implements UserProjectHistoryService {
    private final UserProjectHistoryRepository userProjectHistoryRepository;

    @Override
    public UserProjectHistory toUserProjectHistoryEntity(User user, Project project) {
        // 사용자 프로젝트 이력 생성
        return UserProjectHistory.builder()
                .user(user)
                .project(project)
                .startDate(LocalDate.now())
                .endDate(project.getEndDate())
                .status(UserProjectHistoryStatus.PARTICIPATING)
                .build();
    }

    @Override
    public UserProjectHistory save(UserProjectHistory userProjectHistory) {
        return userProjectHistoryRepository.save(userProjectHistory);
    }

    @Override
    public Long getUserProjectHistoryTotalCount(Long userId) {
        return userProjectHistoryRepository.countUserProjectHistoryByUserId(userId);
    }

    @Override
    public UserProjectHistoryPaginationResponseDto getUserProjectHistoryList(
            Long userId, int pageNumber) {
        return userProjectHistoryRepository.findAllByUserIdOrderByUpdateDateDesc(
                userId, PageRequest.of(pageNumber, 5));
    }

    @Override
    public List<UserProjectHistory> getUserProjectHistoryListParticipates(Long userId, int pageIndex, int itemCount) {
        Pageable pageable = PageRequest.of(pageIndex, itemCount);
        return userProjectHistoryRepository.findAllUserParticipates(userId, pageable);
    }

    @Override
    public Long getUserProjectHistoryParticipatesTotalCount(Long userId) {
        return userProjectHistoryRepository.countParticipatesUserProjectHistoryByUserId(userId);
    }
}
