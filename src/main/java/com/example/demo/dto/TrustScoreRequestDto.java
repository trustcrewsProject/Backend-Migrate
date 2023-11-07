package com.example.demo.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrustScoreRequestDto {
    private int score;

    public TrustScoreRequestDto(int score) {
        this.score = score;
    }
}
