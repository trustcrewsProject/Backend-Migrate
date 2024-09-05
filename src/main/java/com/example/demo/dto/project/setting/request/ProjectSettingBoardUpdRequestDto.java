package com.example.demo.dto.project.setting.request;

import com.example.demo.dto.project.ProjectDetailAuthDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProjectSettingBoardUpdRequestDto {
    private Long projectId;
    private Long boardId;
    private ProjectDetailAuthDto authMap;
    private String title;
    private String content;
    private boolean recruitmentStatus;
    private String contact;
    private List<Long> positionIds;
}