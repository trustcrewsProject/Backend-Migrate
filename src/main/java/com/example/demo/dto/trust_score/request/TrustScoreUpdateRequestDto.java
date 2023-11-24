package com.example.demo.dto.trust_score.request;

import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class TrustScoreUpdateRequestDto {
    @NotNull Long userId;
    Long projectId;
    Long milestoneId;
    Long workId;
    Boolean isPlus;
    Boolean isNewUser;
    Boolean isQuit;
    Boolean isForceQuit;
}
