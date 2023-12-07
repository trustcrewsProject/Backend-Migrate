package com.example.demo.dto.trust_score;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectUserHistoryDto {
    Long id;
    Boolean completeStatus;
    String content;
    Integer trustScore;
    LocalDateTime createDate;

    @QueryProjection
    public ProjectUserHistoryDto(
            Long workId, Integer score, Boolean completeStatus, String content, LocalDateTime createDate) {
        this.id = workId;
        this.trustScore = score;
        this.completeStatus = completeStatus;
        this.content = content;
        this.createDate = createDate;
    }
}
