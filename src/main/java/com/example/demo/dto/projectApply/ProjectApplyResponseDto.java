package com.example.demo.dto.projectApply;

import com.example.demo.constant.ProjectApplyStatus;
import com.example.demo.dto.common.ConstantDto;
import com.example.demo.global.util.LocalDateTimeFormatSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProjectApplyResponseDto {

    private Long project_apply_id;
    private Long project_id;

    private String project_name;

    private String position_name;

    private ConstantDto<ProjectApplyStatus> status;

    private String apply_message;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime createDate;

    public ProjectApplyResponseDto(Long project_apply_id, Long project_id, String name, String position_name, ConstantDto<ProjectApplyStatus> status, String apply_message, LocalDateTime createDate){
        this.project_apply_id = project_apply_id;
        this.project_id = project_id;
        this.project_name = name;
        this.position_name = position_name;
        this.status = status;
        this.apply_message = apply_message;
        this.createDate = createDate;
    }

}
