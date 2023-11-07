package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrustGradeResponseDto {
    private Long id;
    private String name;
    private int trustScore;

    public TrustGradeResponseDto(Long id, String name, int trustScore) {
        this.id = id;
        this.name = name;
        this.trustScore = trustScore;
    }
}
