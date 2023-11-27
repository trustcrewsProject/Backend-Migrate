package com.example.demo.dto.trust_score;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectUserHistoryDto {
    Long workId;
    Integer score;
}
