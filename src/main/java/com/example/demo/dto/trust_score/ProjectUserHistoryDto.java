package com.example.demo.dto.trust_score;

import java.time.LocalDateTime;

import com.example.demo.global.util.LocalDateTimeFormatSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectUserHistoryDto {
    Long id;
    Boolean completeStatus;
    String content;
    Integer trustScore;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    LocalDateTime createDate;

    public ProjectUserHistoryDto(
            Long workId,
            Integer score,
            Boolean completeStatus,
            String content,
            LocalDateTime createDate) {
        this.id = workId;
        this.trustScore = score;
        this.completeStatus = completeStatus;
        this.content = content;
        this.createDate = createDate;
    }
}
