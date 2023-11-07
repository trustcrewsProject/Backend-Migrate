package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTrustGradeResponseDto {
    public Long id;
    public String name;
    public int trustScore;

    public UserTrustGradeResponseDto(Long id, String name, int trustScore) {
        this.id = id;
        this.name = name;
        this.trustScore = trustScore;
    }
}
