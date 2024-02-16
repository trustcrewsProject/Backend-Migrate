package com.example.demo.service.work;

import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface WorkService {

    public Work save(Work work);

    public Work findById(Long id);

    public List<Work> findWorksByProject(Project project);

    public Work findLastCompleteWork(Project project, User user);

    public PaginationResponseDto findWorksByProjectAndMilestone(Long projectId, Long milestoneId, Pageable pageable);

    public void delete(Work work);

    // 특정 프로젝트에 할당된 특정 회원의 업무 내역 + 업무 신뢰점수 내역 조회
    PaginationResponseDto findWorksWithTrustScoreHistoryByProjectIdAndAssignedUserId(Long projectId, Long assignedUserId, Pageable pageable);

    void deleteAllByProject(Project project);
}
