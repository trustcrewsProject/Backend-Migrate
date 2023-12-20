package com.example.demo.dto.user.response;

import com.example.demo.constant.UserProjectHistoryStatus;
import com.example.demo.global.util.LocalDateTimeFormatSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class UserProjectHistoryInfoResponseDto {

    private Long userProjectHistoryId;

    private UserProjectHistoryStatus status;

    private String projectName;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime updateDate;
}
