package com.example.demo.dto;

import com.example.demo.model.TrustGrade;
import com.example.demo.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTrustGradeResponseDto {
    public Long id;
    public String name;
    public String password;
    public int trustScore;
    public TrustGrade trustGrade;

    public UserTrustGradeResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.trustScore = user.getTrustScore();
        this.trustGrade = user.getTrustGrade();
    }
}
