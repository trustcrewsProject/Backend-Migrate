package com.example.demo.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTrustScoreRequestDto  {
    private int trustScore;

    public UserTrustScoreRequestDto (int trustScore) {
        this.trustScore = trustScore;
    }
}
