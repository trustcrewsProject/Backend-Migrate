package com.example.demo.service.alert;

import com.example.demo.constant.AlertType;
import com.example.demo.global.exception.customexception.AlertCustomException;
import com.example.demo.model.alert.Alert;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.user.User;
import com.example.demo.repository.alert.AlertRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {
    private final AlertRepository alertRepository;

    public Alert toAlertEntity(Project project, User user, Position position) {
        return Alert.builder()
                .project(project)
                .checkUser(project.getUser())
                .sendUser(user)
                .work(null)
                .content("프로젝트 지원했습니다.")
                .position(position)
                .type(AlertType.RECRUIT)
                .checked_YN(false)
                .build();
    }

    public Alert toAlertEntity(ProjectMember projectMember, Position position) {
        return Alert.builder()
                .project(projectMember.getProject())
                .checkUser(projectMember.getProject().getUser())
                .sendUser(projectMember.getUser())
                .work(null)
                .content("프로젝트 지원했습니다.")
                .position(position)
                .type(AlertType.RECRUIT)
                .checked_YN(false)
                .build();
    }

    @Override
    public Alert findById(Long id) {
        return alertRepository.findById(id).orElseThrow(() -> AlertCustomException.NOT_FOUND_ALERT);
    }

    @Override
    public Alert save(Alert alert) {
        return alertRepository.save(alert);
    }

    public List<Alert> findAlertsByProjectId(Project project) {
        return alertRepository
                .findAlertsByProject(project)
                .orElseThrow(() -> AlertCustomException.NOT_FOUND_ALERT);
    }

    public List<Alert> findRecruitAlertsByProject(Project project) {
        return alertRepository
                .findRecruitAlertsByProject(project)
                .orElseThrow(() -> AlertCustomException.NOT_FOUND_ALERT);
    }

    public List<Alert> findWorkAlertsByProject(Project project) {
        return alertRepository
                .findWorkAlertsByProject(project)
                .orElseThrow(() -> AlertCustomException.NOT_FOUND_ALERT);
    }

    public List<Alert> findCrewAlertsByProject(Project project) {
        return alertRepository
                .findCrewAlertsByProject(project)
                .orElseThrow(() -> AlertCustomException.NOT_FOUND_ALERT);
    }
}
