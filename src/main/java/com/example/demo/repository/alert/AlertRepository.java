package com.example.demo.repository.alert;

import com.example.demo.model.alert.Alert;
import com.example.demo.model.project.Project;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.work.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlertRepository extends JpaRepository<Alert, Long>, AlertRepositoryCustom {

    Optional<List<Alert>> findAlertsByProject(Project project);

    @Query(
            "select alert from Alert alert where alert.project = :#{#project} and alert.type = com.example.demo.constant.AlertType.RECRUIT")
    Optional<List<Alert>> findRecruitAlertsByProject(@Param(value = "project") Project project);

    @Query(
            "select alert from Alert alert where alert.project = :#{#project} and alert.type = com.example.demo.constant.AlertType.WORK")
    Optional<List<Alert>> findWorkAlertsByProject(@Param(value = "project") Project project);

    @Query(
            "select alert from Alert alert where alert.project = :#{#project} and (alert.type = com.example.demo.constant.AlertType.ADD or alert.type = com.example.demo.constant.AlertType.WITHDRAWAL or alert.type = com.example.demo.constant.AlertType.FORCED_WITHDRAWAL)")
    Optional<List<Alert>> findCrewAlertsByProject(@Param(value = "project") Project project);

    void deleteAllByProject(Project project);

    void deleteAllByWork(Work work);
}
