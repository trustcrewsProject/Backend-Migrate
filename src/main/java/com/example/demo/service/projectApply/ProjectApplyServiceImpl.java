package com.example.demo.service.projectApply;

import com.example.demo.dto.projectApply.ProjectApplyResponseDto;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.projectApply.ProjectApply;
import com.example.demo.model.user.User;
import com.example.demo.repository.projectApply.ProjectApplyRepository;
import com.example.demo.service.position.PositionService;
import com.example.demo.service.project.ProjectService;
import com.example.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectApplyServiceImpl implements ProjectApplyService{
    private final UserService userService;

    private final ProjectService projectService;

    private final PositionService positionService;

    private final ProjectApplyRepository projectApplyRepository;
    @Override
    public ProjectApply toProjectApplyEntity(long userId, long projectId, long positionId, String apply_message) {
        User user = userService.findById(userId);
        Project project = projectService.findById(projectId);
        Position position = positionService.findById(positionId);

        ProjectApply projectApply =  ProjectApply.builder()
                .user(user)
                .project(project)
                .position(position)
                .apply_message(apply_message)
                .build();

        return projectApplyRepository.save(projectApply);
    }

    @Override
    public List<ProjectApplyResponseDto> findProjectAppliesByUserId(Long userId, Pageable pageable) {
        return projectApplyRepository.findProjectAppliesByUserId(userId, pageable);
    }

    @Override
    public Long countProjectAppliesByUserId(Long userId) {
        return projectApplyRepository.countProjectAppliesByUserId(userId);
    }
}
