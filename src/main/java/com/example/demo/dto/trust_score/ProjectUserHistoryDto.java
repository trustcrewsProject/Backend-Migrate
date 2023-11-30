package com.example.demo.dto.trust_score;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProjectUserHistoryDto {
    Long id;
    Boolean completeStatus;
    String name;
    Integer trustScore;
    Date date;
    @QueryProjection
    public ProjectUserHistoryDto(Long workId, Integer score, Boolean completeStatus, String content, Date createDate) {
        this.id = workId;
        this.trustScore = score;
        this.completeStatus = completeStatus;
        this.name = content;
        this.date = createDate;
    }
}