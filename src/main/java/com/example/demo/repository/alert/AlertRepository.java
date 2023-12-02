package com.example.demo.repository.alert;

import com.example.demo.model.alert.Alert;
import com.example.demo.model.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    Optional<List<Alert>> findAlertsByProject(Project project);

    @Query("select alert from Alert alert where alert.project = :#{project.id} and alert.type = 'RECRUIT'")
    Optional<List<Alert>> findRecruitAlertsByProject(@Param(value ="project") Project project);

    @Query("select alert from Alert alert where alert.project = :#{project.id} and alert.type = 'WORK'")
    Optional<List<Alert>> findWorkAlertsByProject(Project project);

    @Query("select alert from Alert alert where alert.project = :#{project.id} and alert.type = 'ADD' or alert.type = 'WITHDRAWL' or alert.type = 'FORCEDWITHDRAWL'")
    Optional<List<Alert>> findCrewAlertsByProject(Project project);
}
