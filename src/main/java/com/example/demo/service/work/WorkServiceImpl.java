package com.example.demo.service.work;

import com.example.demo.constant.ProgressStatus;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.global.exception.customexception.WorkCustomException;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import com.example.demo.repository.work.WorkRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkServiceImpl implements WorkService {
    private final WorkRepository workRepository;

    public Work save(Work work) {
        return workRepository.save(work);
    }

    public Work findById(Long id) {
        return workRepository.findById(id).orElseThrow(() -> WorkCustomException.NOT_FOUND_WORK);
    }

    @Override
    public List<Work> findWorksByProject(Project project) {
        return workRepository
                .findWorksByProject(project)
                .orElseThrow(() -> WorkCustomException.NOT_FOUND_WORK);
    }


    public Work findLastCompleteWork(Project project, User user) {
        return workRepository
                .findFirstByProjectAndAssignedUserIdAndProgressStatusOrderByIdDesc(
                        project, user, ProgressStatus.COMPLETION)
                .orElse(null);
    }

    public PaginationResponseDto findWorksByProjectAndMilestone(Long projectId, Long milestoneId, Pageable pageable) {
        return workRepository
                .findWorkByProjectIdAndMilestoneIdOrderByStartDateAsc(projectId, milestoneId, pageable);
    }

    public void delete(Work work) { workRepository.delete(work); }

    @Override
    public void deleteAllByProject(Project project) {
        workRepository.deleteAllByProject(project);
    }

    @Override
    public void deleteAllByMilestoneId(Long milestoneId) {
        workRepository.deleteAllByMilestoneId(milestoneId);
    }

    @Override
    public int countWorksByMilestoneId(Long milestoneId) {
        return workRepository.countWorksByMilestoneId(milestoneId);
    }
}
