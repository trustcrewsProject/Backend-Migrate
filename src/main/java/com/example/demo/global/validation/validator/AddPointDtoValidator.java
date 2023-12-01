package com.example.demo.global.validation.validator;

import com.example.demo.dto.trust_score.AddPointDto;
import com.example.demo.global.exception.customexception.ProjectCustomException;
import com.example.demo.global.exception.customexception.ProjectMemberCustomException;
import com.example.demo.global.exception.customexception.UserCustomException;
import com.example.demo.global.exception.customexception.WorkCustomException;
import com.example.demo.global.validation.annotation.ValidAddPointDto;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import com.example.demo.repository.project.ProjectMemberRepository;
import com.example.demo.repository.project.ProjectRepository;
import com.example.demo.repository.trust_score.TrustScoreRepository;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.repository.work.WorkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.Optional;

import static com.example.demo.constant.TrustScoreTypeIdentifier.*;
/*
TODO : 1. Clean Code 2. CustomException 생성
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AddPointDtoValidator implements ConstraintValidator<ValidAddPointDto, AddPointDto> {
    private final UserRepository userRepository;
    private final WorkRepository workRepository;
    private final TrustScoreRepository trustScoreRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;
    @Override
    public void initialize(ValidAddPointDto constraintAnnotation) {
    }

    /**
     * AddPointDto 유효성 검증(validate)
     * @param dto object to validate
     * @param context context in which the constraint is evaluated
     * @return
     */
    @Override
    public boolean isValid(AddPointDto dto, ConstraintValidatorContext context) {
        Long userId = dto.getUserId();
        Long scoreTypeId = dto.getScoreTypeId();
        Long projectId = dto.getProjectId();
        Long milestoneId = dto.getMilestoneId();
        Long workId = dto.getWorkId();
        if (scoreTypeId.equals(WORK_COMPLETE)) {
            User user = userRepository.findById(userId).orElseThrow(() -> UserCustomException.NOT_FOUND_USER);
            Work work = workRepository.findById(workId).orElseThrow(() -> WorkCustomException.NOT_FOUND_WORK);
            if (!trustScoreRepository.existsByUserId(userId)) {
                throw new RuntimeException();
            }
            try {
                Long findProjectId = work.getProject().getId();
                Long findMilestoneId = work.getMilestone().getId();
                if (!findProjectId.equals(projectId) || !findMilestoneId.equals(milestoneId)) {
                    throw new RuntimeException();
                }
            } catch (NullPointerException npe) {
                log.error("업무가 프로젝트, 마일스톤 값을 가지고 있지 않음.", npe);
            }
            if (!work.isCompleteStatus()) {
                throw new RuntimeException();
            }
            if (!work.getAssignedUserId().equals(user)) {
                throw new RuntimeException();
            }
        } else if (scoreTypeId.equals(WORK_INCOMPLETE)) {
            User user = userRepository.findById(userId).orElseThrow(() -> UserCustomException.NOT_FOUND_USER);
            Work work = workRepository.findById(workId).orElseThrow(() -> WorkCustomException.NOT_FOUND_WORK);
            if (!trustScoreRepository.existsByUserId(userId)) {
                throw new RuntimeException();
            }
            try {
                Long findProjectId = work.getProject().getId();
                Long findMilestoneId = work.getMilestone().getId();
                if (!findProjectId.equals(projectId) || !findMilestoneId.equals(milestoneId)) {
                    throw new RuntimeException("업무의 프로젝트 ID와 혹은 마일스톤 ID와 입력 값이 다름");
                }
            } catch (NullPointerException npe) {
                log.error("업무가 프로젝트, 마일스톤 값을 가지고 있지 않음.", npe);
            }
            if (!work.isCompleteStatus()) {
                throw new RuntimeException("업무가 완성이 되지 않았음");
            }
            if (!work.getAssignedUserId().equals(user)) {
                throw new RuntimeException("업무 배정된 사용자와 입력값이 다름");
            }
        } else if (scoreTypeId.equals(NEW_MEMBER)) {
            if (trustScoreRepository.existsByUserId(userId)) {
                throw new RuntimeException("신뢰점수 테이블엔 있는 회원임");
            }
            return projectId == null && milestoneId == null && workId == null;
        } else if (scoreTypeId.equals(SELF_WITHDRAWAL)) {
            Project findProject = projectRepository.findById(projectId)
                    .orElseThrow(() -> ProjectCustomException.NOT_FOUND_PROJECT);
            User findUser = userRepository.findById(userId)
                    .orElseThrow(() -> UserCustomException.NOT_FOUND_USER);
            Optional<ProjectMember> findProjectMember =
                    projectMemberRepository.findProjectMemberByProjectAndUser(findProject, findUser);
            if (findProjectMember.isPresent()) {
                throw new RuntimeException("회원이 아직 있음.");
            }
            if (milestoneId == null && workId == null) {
                throw new RuntimeException("잘못된 입력값. 마일스톤, 업무 아이디 있으면 안 됨");
            }
        } else if (scoreTypeId.equals(FORCE_WITHDRAWAL)) {
            Project findProject = projectRepository.findById(projectId)
                    .orElseThrow(() -> ProjectCustomException.NOT_FOUND_PROJECT);
            User findUser = userRepository.findById(userId)
                    .orElseThrow(() -> UserCustomException.NOT_FOUND_USER);
            Optional<ProjectMember> findProjectMember =
                    projectMemberRepository.findProjectMemberByProjectAndUser(findProject, findUser);
            if (findProjectMember.isPresent()) {
                throw new RuntimeException("회원이 아직 있음.");
            }
            if (milestoneId == null && workId == null) {
                throw new RuntimeException("잘못된 입력값. 마일스톤, 업무 아이디 있으면 안 됨");
            }
        } else {
            throw new RuntimeException("1L~5L까지만 가능함");
        }
        return true;
    }
}