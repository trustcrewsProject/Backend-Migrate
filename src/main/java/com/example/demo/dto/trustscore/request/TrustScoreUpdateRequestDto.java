package com.example.demo.dto.trustscore.request;

import lombok.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class TrustScoreUpdateRequestDto {
    @NotNull
    Long userId;
    Long projectId;
    Long milestoneId;
    Long workId;
    Boolean isPlus;
    Boolean isNewUser;
    Boolean isQuit;
    Boolean isForceQuit;
}

/*
1. 회원가입
2. 업무평가
3. 프로젝트 탈퇴
4. 프로젝트 강제 탈퇴
5. 프로젝트 회원 이력
*/
