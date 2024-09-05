package com.example.demo.dto.project.request;

import static com.example.demo.constant.ProjectStatus.RECRUITING;

import com.example.demo.model.project.Project;
import com.example.demo.model.trust_grade.TrustGrade;
import com.example.demo.model.user.User;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

// 프로젝트 개설 요청 DTO
@Getter
@AllArgsConstructor
public class ProjectCreateRequestDto {
    @NotBlank(message = "프로젝트명은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "프로젝트 주제명은 필수 입력 값입니다.")
    private String subject;

    @NotBlank(message = "시작날짜는 필수 입력 값입니다.")
    private LocalDate startDate;

    @NotBlank(message = "종료날짜는 필수 입력 값입니다.")
    private LocalDate endDate;

    @NotBlank(message = "모집분야는 필수 입력 값입니다.")
    private List<Long> technologyIds;

    public Project toProjectEntity(TrustGrade trustGrade, User user) {
        return Project.builder()
                .name(this.getName())
                .subject(this.getSubject())
                .trustGrade(trustGrade)
                .user(user)
                .status(RECRUITING)
                .startDate(this.getStartDate())
                .endDate(this.getEndDate())
                .build();
    }
}
