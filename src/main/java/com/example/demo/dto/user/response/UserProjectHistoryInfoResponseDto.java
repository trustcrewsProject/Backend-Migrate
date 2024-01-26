package com.example.demo.dto.user.response;

import com.example.demo.constant.UserProjectHistoryStatus;
import com.example.demo.global.util.LocalDateTimeFormatSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserProjectHistoryInfoResponseDto {

    private Long userProjectHistoryId;

    private Long projectId;

    private UserProjectHistoryStatus status;

    private String projectName;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime updateDate;
}
