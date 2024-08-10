package com.example.demo.repository.projectAlert.crew;

import com.example.demo.model.project.alert.crew.AlertCrew;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertCrewRepository extends JpaRepository<AlertCrew, Long>, AlertCrewRepositoryCustom {

}
