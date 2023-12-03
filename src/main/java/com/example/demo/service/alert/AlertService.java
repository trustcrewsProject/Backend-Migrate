package com.example.demo.service.alert;

import com.example.demo.dto.alert.AlertCreateRequestDto;
import com.example.demo.model.alert.Alert;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AlertService {
    
    public Alert findById(Long id);
    public Alert save(Alert alert);

    public List<Alert> findAlertsByProjectId(Project project);

    public List<Alert> findRecruitAlertsByProject(Project project);

    public List<Alert> findWorkAlertsByProject(Project project);

    public List<Alert> findCrewAlertsByProject(Project project);
}
