package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class TrustGradeRequestDto {
    public String name;
    public int trustScore;

    public TrustGradeRequestDto(String name, int trustScore) {
        this.name = name;
        this.trustScore = trustScore;
    }
}
