package com.example.demo.alert.service;


import com.example.demo.model.alert.Alert;
import com.example.demo.model.user.User;
import com.example.demo.service.alert.AlertService;
import com.example.demo.service.user.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class AlertServiceTest {

    @Autowired
    private AlertService alertService;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("알림 초기값 확인")
    public void checkAlertState() {
        // given
        Alert alert = Alert.builder().build();

        // when
        Alert saveAlert = alertService.save(alert);

        // then
        Assertions.assertThat(saveAlert.isChecked_YN()).isFalse();

    }

    @Test
    @DisplayName("알림 상태 변경")
    public void changeAlertService() {
        // given
        User user = User.builder().build();
        User saveUser = userService.save(user);
        Alert alert = Alert.builder()
                        .checkUser(saveUser)
                        .sendUser(saveUser)
                        .build();
        Alert saveAlert = alertService.save(alert);

        // when
        alertService.updateAlertStatus(saveAlert.getId());
        em.flush();
        em.clear();
        Alert updateAlert = alertService.findById(saveAlert.getId());


        // then
        Assertions.assertThat(updateAlert.isChecked_YN()).isTrue();

    }
}
