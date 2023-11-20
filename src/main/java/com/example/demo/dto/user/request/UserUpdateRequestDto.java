package com.example.demo.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserUpdateRequestDto {

    private String nickname;

    private Long positionId;

    private List<Long> techStackIds;

    private String intro;
}
