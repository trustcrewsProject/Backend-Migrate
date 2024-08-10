package com.example.demo.repository.projectAlert.vote.recruit;

import com.example.demo.model.project.alert.vote.VAlertRecruit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VAlertRecruitRepository extends JpaRepository<VAlertRecruit, Long>, VAlertRecruitRepositoryCustom {

}
