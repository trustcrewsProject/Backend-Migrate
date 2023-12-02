package com.example.demo.service.alert;

import com.example.demo.constant.AlertType;
import com.example.demo.dto.alert.AlertCreateRequestDto;
import com.example.demo.global.exception.customexception.AlertCustomException;
import com.example.demo.model.alert.Alert;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import com.example.demo.repository.alert.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {
    private final AlertRepository alertRepository;

    public Alert toAlertEntity(
            AlertCreateRequestDto alertCreateRequestDto,
            Project project,
            User checkUser,
            User sendUser,
            Work work,
            Position position
    ){
        return Alert.builder()
                .project(project)
                .checkUser(checkUser)
                .sendUser(sendUser)
                .work(work)
                .position(position)
                .content(alertCreateRequestDto.getContent())
                .type(alertCreateRequestDto.getType())
                .checked_YN(alertCreateRequestDto.getChecked_yn())
                .build();
    }

    public Alert toAlertEntity(Project project, User user, Position position){
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

    public Alert toAlertEntity(ProjectMember projectMember, Position position){
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

    public List<Alert> findAlertsByProjectId(Project project){
        return alertRepository.findAlertsByProject(project).orElseThrow(() -> AlertCustomException.NOT_FOUND_ALERT);
    }
}
