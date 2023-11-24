package com.example.demo.dto.user.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateRequestDto {

    private String nickname;

    private Long positionId;

    private List<Long> techStackIds;

    private String intro;
}
