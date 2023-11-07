package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrustGradeResponseDto {

    public int id;
    public String name;
    public int trustScore;

    public TrustGradeResponseDto(int id, String name, int trustScore) {
        this.id = id;
        this.name = name;
        this.trustScore = trustScore;
    }
}
