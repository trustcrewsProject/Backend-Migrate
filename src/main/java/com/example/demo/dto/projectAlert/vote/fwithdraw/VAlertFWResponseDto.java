package com.example.demo.dto.projectAlert.vote.fwithdraw;

import com.example.demo.constant.ProjectAlertType;
import com.example.demo.constant.VoteStatus;
import com.example.demo.dto.common.ConstantDepthDto;
import com.example.demo.dto.common.ConstantDto;
import com.example.demo.global.util.LocalDateTimeFormatSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import org.apache.tomcat.util.bcel.Const;

import java.time.LocalDateTime;

@Getter
@Builder
public class VAlertFWResponseDto {

    private Long alertId;
    private Long voteId;
    private Long fwMemberId;
    private ConstantDepthDto<ProjectAlertType> alertType;
    private String contents;
    private ConstantDto<VoteStatus> voteStatus;
    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime createDate;

    public VAlertFWResponseDto(
            Long alertId,
            Long voteId,
            Long fwMemberId,
            ConstantDepthDto<ProjectAlertType> alertType,
            String contents,
            ConstantDto<VoteStatus> voteStatus,
            LocalDateTime createDate
    ) {
        this.alertId = alertId;
        this.voteId = voteId;
        this.fwMemberId = fwMemberId;
        this.alertType = alertType;
        this.contents = contents;
        this.voteStatus = voteStatus;
        this.createDate = createDate;
    }

}
