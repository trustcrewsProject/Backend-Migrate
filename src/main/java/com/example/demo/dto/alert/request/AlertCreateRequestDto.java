package com.example.demo.dto.alert;

import com.example.demo.constant.AlertType;
import com.example.demo.model.alert.Alert;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlertCreateRequestDto {
    private Long projectId;
    private Long checkUserId;
    private Long sendUserId;
    private Long workId;
    private Long positionId;
    private AlertType type;
    private String content;
    private Boolean checked_yn;

    public Alert toAlertEntity(
            Project project, User checkUser, User sendUser, Work work, Position position) {
        return Alert.builder()
                .project(project)
                .checkUser(checkUser)
                .sendUser(sendUser)
                .work(work)
                .position(position)
                .content(this.getContent())
                .type(this.getType())
                .checked_YN(this.getChecked_yn())
                .build();
    }
}
