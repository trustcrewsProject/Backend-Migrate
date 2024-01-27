package com.example.demo.service.user;

import com.example.demo.constant.UserProjectHistoryStatus;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserProjectHistory;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserProjectHistoryService {

    public UserProjectHistory toUserProjectHistoryEntity(User user, Project project);

    public UserProjectHistory save(UserProjectHistory userProjectHistory);

    // 회원 프로젝트 이력 전체 개수 조회
    Long getUserProjectHistoryTotalCount(Long userId, UserProjectHistoryStatus status);

    // 회원 프로젝트 이력 조회
    PaginationResponseDto getUserProjectHistoryList(Long userId, int pageNumber);

    // 회원 참여중인 프로젝트 이력 조회
    List<UserProjectHistory> getUserProjectHistoryListParticipates(Long userId, int pageIndex, int itemCount);
}
