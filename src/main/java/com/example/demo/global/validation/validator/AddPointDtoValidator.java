package com.example.demo.global.validation.validator;

import com.example.demo.dto.trust_score.AddPointDto;
import com.example.demo.global.exception.customexception.ProjectCustomException;
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
import com.example.demo.repository.trust_score.TrustScoreTypeRepository;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.repository.work.WorkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Optional;

import static com.example.demo.constant.TrustScoreTypeIdentifier.*;
/*
TODO : CustomException 생성
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
    private final TrustScoreTypeRepository trustScoreTypeRepository;
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

        validateScoreType(scoreTypeId);

        if (scoreTypeId.equals(WORK_COMPLETE) || scoreTypeId.equals(WORK_INCOMPLETE)) {
            validateWorkAndUser(userId, workId, projectId, milestoneId);
        } else if (scoreTypeId.equals(NEW_MEMBER)) {
            validateNewMember(userId, projectId, milestoneId, workId);
        }
        else if (scoreTypeId.equals(SELF_WITHDRAWAL) || scoreTypeId.equals(FORCE_WITHDRAWAL)) {
            validateWithdrawal(projectId, userId, milestoneId, workId);
        } else {
            throw new RuntimeException("1L~5L까지만 가능함");
        }
        return true;
    }

    /**
     *
     * @param scoreTypeId
     */

    private void validateScoreType(Long scoreTypeId) {
        List<Long> upScoreTypeListList = trustScoreTypeRepository.findAllUpScoreTypeId();
        if (!upScoreTypeListList.contains(scoreTypeId)) {
            throw new RuntimeException("입력값과 매칭되는 대분류 신뢰점수타입이 없습니다");
        }
    }

    /**
     * 자진탈퇴, 강제탈퇴 유효성 검증
     * @param projectId
     * @param userId
     * @param milestoneId
     * @param workId
     */
    private void validateWithdrawal(Long projectId, Long userId, Long milestoneId, Long workId) {
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
    }

    /**
     * 신규회원 유효성 검증
     * @param userId
     * @param projectId
     * @param milestoneId
     * @param workId
     */
    private void validateNewMember(Long userId, Long projectId, Long milestoneId, Long workId) {
        if (trustScoreRepository.existsByUserId(userId)) {
            throw new RuntimeException("신뢰점수 테이블엔 있는 회원임");
        }
        if (projectId != null || milestoneId != null || workId != null) {
            throw new RuntimeException("");
        }
    }

    /**
     * 업무완수 및 미흡 유효성 검증
     * @param userId
     * @param workId
     * @param projectId
     * @param milestoneId
     */
    private void validateWorkAndUser(Long userId, Long workId, Long projectId, Long milestoneId) {
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
            throw new RuntimeException("업무가 프로젝트, 마일스톤 값을 가지고 있지 않음");
        }
        if (!work.isCompleteStatus()) {
            throw new RuntimeException("업무 미완성이다.");
        }
        if (!work.getAssignedUserId().equals(user)) {
            throw new RuntimeException("업무 배정 사용자랑 입력값 사용자가 다르다");
        }
    }
}