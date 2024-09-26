package com.example.demo.service.user;

import com.example.demo.constant.UserProjectHistoryStatus;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.global.exception.customexception.UserProjectHistoryCustomException;
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
    public UserProjectHistory toUserProjectHistoryEntity(User user, Project project, UserProjectHistoryStatus status) {
        // 사용자 프로젝트 이력 생성
        return UserProjectHistory.builder()
                .user(user)
                .project(project)
                .startDate(LocalDate.now())
                .endDate(project.getEndDate())
                .status(status)
                .build();
    }

    @Override
    public UserProjectHistory save(UserProjectHistory userProjectHistory) {
        return userProjectHistoryRepository.save(userProjectHistory);
    }

    @Override
    public Long getUserProjectHistoryTotalCount(Long userId, UserProjectHistoryStatus status) {
        return userProjectHistoryRepository.countUserProjectHistory(userId, status);
    }

    @Override
    public PaginationResponseDto getUserProjectHistoryList(
            Long userId, int pageNumber) {
        return userProjectHistoryRepository.findAllByUserIdOrderByUpdateDateDesc(
                userId, PageRequest.of(pageNumber, 5));
    }
}
