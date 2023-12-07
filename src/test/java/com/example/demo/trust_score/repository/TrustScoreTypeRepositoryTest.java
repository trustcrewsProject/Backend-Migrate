package com.example.demo.trust_score.repository;

import com.example.demo.global.exception.customexception.TrustGradeCustomException;
import com.example.demo.model.project.Project;
import com.example.demo.model.trust_grade.TrustGrade;
import com.example.demo.repository.project.ProjectRepository;
import com.example.demo.repository.trust_grade.TrustGradeRepository;
import com.example.demo.repository.trust_score.TrustScoreTypeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.example.demo.constant.TrustScoreTypeIdentifier.*;

@SpringBootTest
@Transactional
public class TrustScoreTypeRepositoryTest {

    @Autowired
    private TrustScoreTypeRepository trustScoreTypeRepository;

    @Autowired
    private TrustGradeRepository trustGradeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    @DisplayName("getScore 메서드 테스트 - 성공")
    public void getScore_MethodTest_Pass() {
        // given
        Long scoreTypeId = NEW_MEMBER;

        // when
        int score = trustScoreTypeRepository.getScore(scoreTypeId);

        // then
        Assertions.assertThat(score).isEqualTo(200);
    }

    @Test
    @DisplayName("getScore 메서드 테스트 - 실패. " +
            "원인 : 존재하지 않는 신뢰점수타입 식별자")
    public void getScore_MethodTest_Fail() {
        // given
        Long scoreTypeId = 30L;

        // when - then
        Assertions.assertThatThrownBy(
                () -> trustScoreTypeRepository.getScore(scoreTypeId))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("getScoreByProject 메서드 테스트 - 성공")
    public void getScoreByProject_MethodTest_Pass() {
        // given
        // 4등급 프로젝트 생성
        TrustGrade trustGrade = trustGradeRepository.findById(4L)
                .orElseThrow(() -> TrustGradeCustomException.NOT_FOUND_TRUST_GRADE);
        Project project = Project.builder()
                .name("테스트 프로젝트")
                .trustGrade(trustGrade)
                .build();
        Project saveProject = projectRepository.save(project);

        // when
        Long trustScoreTypeId = WORK_COMPLETE;
        int scoreByProject = trustScoreTypeRepository
                .getScoreByProject(saveProject.getId(), trustScoreTypeId);

        // then
        Assertions.assertThat(scoreByProject).isEqualTo(20);
    }

    // TODO : 테스트 필요 여부 재검증
    @Test
    @DisplayName("getScoreByProject 메서드 테스트 - 실패 " +
            "원인 : 해당 프로젝트 존재하지 않음")
    public void getScoreByProject_MethodTest_Fail() {
        // given
        Long projectId = 10000000000000L;
        Long trustScoreTypeId = WORK_COMPLETE;

        // when - then
        Assertions.assertThatThrownBy(
                () -> trustScoreTypeRepository.getScoreByProject(projectId, trustScoreTypeId))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("findAllUpScoreTypeId 메서드 테스트 - 성공")
    public void findAllUpScoreTypeId_MethodTest_Pass() {
        // given
        List<Long> scoreTypeIdentifierList = new ArrayList<>(Arrays.asList(WORK_COMPLETE, WORK_INCOMPLETE,
                NEW_MEMBER, SELF_WITHDRAWAL, FORCE_WITHDRAWAL, LATE_WORK));

        // when
        List<Long> upScoreTypeIdList =
                trustScoreTypeRepository.findAllUpScoreTypeId();

        // then
        Assertions.assertThat(scoreTypeIdentifierList)
                .isEqualTo(upScoreTypeIdList);
    }
}
