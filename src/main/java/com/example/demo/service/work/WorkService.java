package com.example.demo.service.work;

import com.example.demo.dto.projectmember.response.ProjectMemberWorksPaginationResponseDto;
import com.example.demo.dto.work.response.WorkPaginationResponseDto;
import com.example.demo.dto.work.response.WorkReadResponseDto;
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

    public WorkPaginationResponseDto findWorksByProjectAndMilestone(Long projectId, Long milestoneId, Pageable pageable);

    public void delete(Long workId);

    // 특정 프로젝트에 할당된 특정 회원의 업무 내역 + 업무 신뢰점수 내역 조회
    ProjectMemberWorksPaginationResponseDto findWorksWithTrustScoreHistoryByProjectIdAndAssignedUserId(Long projectId, Long assignedUserId, Pageable pageable);
}
