package com.example.demo.dto.work.request;

import java.time.LocalDate;

import com.example.demo.constant.ProgressStatus;
import com.example.demo.constant.ProjectMemberAuth;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkUpdateRequestDto {
    private Long workId;
    private String content;
    private String contentDetail;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProgressStatus progressStatus;
    private Long assignedUserId;
    private ProjectMemberAuth authMap;
}
