package com.example.demo.repository.projectAlert.vote.fwithdraw;

import com.example.demo.model.project.alert.vote.VAlertFWithdraw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VAlertFWithdrawRepository extends JpaRepository<VAlertFWithdraw, Long>, VAlertFWithdrawRepositoryCustom {

}
