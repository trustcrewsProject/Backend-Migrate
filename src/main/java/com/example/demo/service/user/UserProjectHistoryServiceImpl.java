package com.example.demo.service.user;

import com.example.demo.constant.UserProjectHistoryStatus;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserProjectHistory;
import com.example.demo.repository.user.UserProjectHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserProjectHistoryServiceImpl implements  UserProjectHistoryService {
    private final UserProjectHistoryRepository userProjectHistoryRepository;

    @Override
    public UserProjectHistory toUserProjectHistoryEntity(User user, Project project) {
        //사용자 프로젝트 이력 생성
        return UserProjectHistory.builder()
                .user(user)
                .project(project)
                .startDate(LocalDateTime.now())
                .endDate(project.getEndDate())
                .status(UserProjectHistoryStatus.PARTICIPATING)
                .build();
    }

    @Override
    public UserProjectHistory save(UserProjectHistory userProjectHistory) {
        return userProjectHistoryRepository.save(userProjectHistory);
    }
}
