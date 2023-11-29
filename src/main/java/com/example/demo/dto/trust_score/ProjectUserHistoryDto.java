package com.example.demo.dto.trust_score;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class ProjectUserHistoryDto {
    Long workId;
    Long workStatus;
    String workName;
    Integer scoreChange;
    Date historyDate;
}