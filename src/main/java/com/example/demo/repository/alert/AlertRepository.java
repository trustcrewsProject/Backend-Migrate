package com.example.demo.repository.alert;

import com.example.demo.model.alert.Alert;
import com.example.demo.model.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    Optional<List<Alert>> findAlertsByProject(Project project);
}
