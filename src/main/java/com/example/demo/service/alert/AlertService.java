package com.example.demo.service.alert;

import com.example.demo.model.alert.Alert;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.user.User;
import org.springframework.stereotype.Service;

@Service
public interface AlertService {

    public Alert toAlertEntity(Project project, User user, Position position);

    public Alert toAlertEntity(ProjectMember projectMember, Position position);

    public Alert findById(Long id);
    public Alert save(Alert alert);
}
