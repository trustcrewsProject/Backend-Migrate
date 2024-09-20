package com.example.demo.dto.trust_score;

import com.example.demo.constant.ProgressStatus;
import com.example.demo.dto.common.ConstantDto;
import com.example.demo.global.util.LocalDateTimeFormatSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectUserHistoryDto {
    Long id;
    ConstantDto<ProgressStatus> progressStatus;
    String content;
    Integer trustScore;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    LocalDateTime createDate;

    public ProjectUserHistoryDto(
            Long workId,
            Integer score,
            ConstantDto<ProgressStatus> progressStatus,
            String content,
            LocalDateTime createDate) {
        this.id = workId;
        this.trustScore = score;
        this.progressStatus = progressStatus;
        this.content = content;
        this.createDate = createDate;
    }
}
