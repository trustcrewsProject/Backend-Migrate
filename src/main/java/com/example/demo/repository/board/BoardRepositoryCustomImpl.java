package com.example.demo.repository.board;

import com.example.demo.constant.ProjectStatus;
import com.example.demo.dto.board.Response.BoardSearchResponseDto;
import com.example.demo.dto.boardposition.BoardPositionDetailResponseDto;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.position.response.PositionResponseDto;
import com.example.demo.dto.project.setting.response.ProjectSettingInfoResponseDto;
import com.example.demo.dto.technology_stack.response.TechnologyStackInfoResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeResponseDto;
import com.example.demo.dto.user.response.UserSearchResponseDto;
import com.example.demo.global.exception.customexception.PositionCustomException;
import com.example.demo.global.exception.customexception.TechnologyStackCustomException;
import com.example.demo.model.board.Board;
import com.example.demo.model.board.BoardPosition;
import com.example.demo.model.board.QBoard;
import com.example.demo.model.board.QBoardPosition;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectTechnology;
import com.example.demo.model.project.QProject;
import com.example.demo.model.project.QProjectTechnology;
import com.example.demo.model.technology_stack.TechnologyStack;
import com.example.demo.model.user.QUser;
import com.example.demo.model.user.User;
import com.example.demo.repository.position.PositionRepository;
import com.example.demo.repository.technology_stack.TechnologyStackRepository;
import com.example.demo.service.file.AwsS3FileService;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final PositionRepository positionRepository;
    private final TechnologyStackRepository technologyStackRepository;
    private final AwsS3FileService awsS3FileService;

    public QBoard qBoard = QBoard.board;
    public QProject qProject = QProject.project;
    public QBoardPosition qBoardPosition = QBoardPosition.boardPosition;
    public QUser qUser = QUser.user;

    public QProjectTechnology qProjectTechnology = QProjectTechnology.projectTechnology;

    @Override
    public PaginationResponseDto getBoardSearchPage(
            Long positionId, String keyword, List<Long> technologyIds, Boolean recruitmentStatus, ProjectStatus projectStatus, Pageable pageable) {

        List<Board> boards =
                queryFactory
                        .selectDistinct(qBoard)
                        .from(qBoard)
                        .join(qBoard.positions, qBoardPosition)
                        .join(qBoard.project, qProject)
                        .join(qBoard.user, qUser)
                        .join(qProject.projectTechnologies, qProjectTechnology)
                        .where(eqBoardStatus(recruitmentStatus),
                                neBoardProjectStatus(projectStatus),
                                searchByLike(keyword),
                                containsPosition(positionId),
                                containsProjectTechnologyStack(technologyIds))
                        .orderBy(qBoard.updateDate.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        List<BoardSearchResponseDto> boardSearchResponseDtos = new ArrayList<>();

        for (Board boardEntity : boards) {
            // 게시글 정보 - 모집포지션
            List<BoardPositionDetailResponseDto> boardPositions = new ArrayList<>();
            for (BoardPosition boardPosition : boardEntity.getPositions()) {
                BoardPositionDetailResponseDto boardPositionResponse =
                        BoardPositionDetailResponseDto.of(
                                boardPosition, PositionResponseDto.of(boardPosition.getPosition()));

                boardPositions.add(boardPositionResponse);
            }


            // 게시글 정보 - 프로젝트 정보
            Project project = boardEntity.getProject();
            // 프로젝트 정보 - 기술스택
            List<TechnologyStackInfoResponseDto> technologyStacks = new ArrayList<>();
            for (ProjectTechnology projectTechnology : project.getProjectTechnologies()) {
                TechnologyStack technologyStack = projectTechnology.getTechnologyStack();
                TechnologyStackInfoResponseDto technologyStackInfoResponseDto =
                        TechnologyStackInfoResponseDto.of(
                                technologyStack.getId(), technologyStack.getName());
                technologyStacks.add(technologyStackInfoResponseDto);
            }

            ProjectSettingInfoResponseDto projectDto = ProjectSettingInfoResponseDto.of(project, technologyStacks);

            // 게시글 정보 - 게시글 작성자 정보
            User user = boardEntity.getUser();

            TrustGradeResponseDto userTrustGradeResponseDto = TrustGradeResponseDto.of(user.getTrustScore().getTrustGrade());
            UserSearchResponseDto userSearchResponseDto = UserSearchResponseDto.of(user, awsS3FileService.generatePreSignedUrl(user.getProfileImgSrc()), userTrustGradeResponseDto);

            boardSearchResponseDtos.add(
                    BoardSearchResponseDto.of(
                            boardEntity,
                            boardPositions,
                            projectDto,
                            userSearchResponseDto));
        }

        long totalPages = countBoardBySearchCriteria(positionId, keyword, technologyIds, recruitmentStatus, projectStatus);

        return PaginationResponseDto.of(boardSearchResponseDtos, totalPages);
    }

    @Override
    public Board findByProjectId(Long projectId) {
        return queryFactory
                .selectFrom(qBoard)
                .where(qBoard.project.id.eq(projectId))
                .fetchOne();
    }

    private Long countBoardBySearchCriteria(Long positionId, String keyword, List<Long> technologyIds, Boolean recruitmentStatus, ProjectStatus projectStatus) {
        return queryFactory
                .select(qBoard.id.countDistinct())
                .from(qBoard)
                .join(qBoard.positions, qBoardPosition)
                .leftJoin(qBoard.project, qProject)
                .leftJoin(qProject.projectTechnologies, qProjectTechnology)
                .where(eqBoardStatus(recruitmentStatus),
                        neBoardProjectStatus(projectStatus),
                        searchByLike(keyword),
                        containsPosition(positionId),
                        containsProjectTechnologyStack(technologyIds))
                .fetchOne();
    }

    private BooleanExpression searchByLike(String searchQuery) {
        if (StringUtils.hasText(searchQuery)) {
            return qBoard
                    .title
                    .like("%" + searchQuery + "%")
                    .or(qBoard.content.like("%" + searchQuery + "%"));
        } else {
            return null;
        }
    }

    private BooleanExpression containsPosition(Long positionId) {
        if (positionId != null) {
            Position position =
                    positionRepository
                            .findById(positionId)
                            .orElseThrow(() -> PositionCustomException.NOT_FOUND_POSITION);

            return qBoardPosition.position.in(position);
        } else {
            return null;
        }
    }

    private BooleanExpression containsProjectTechnologyStack(List<Long> technologyIds) {
        if (technologyIds == null || technologyIds.isEmpty()) {
            return null;
        }

        // 기술스택 개수만큼 매칭된 프로젝트 ID만 가져오기
        List<Long> projectIdsWithAllStacks = queryFactory
                .select(qProjectTechnology.project.id)
                .from(qProjectTechnology)
                .where(qProjectTechnology.technologyStack.id.in(technologyIds))
                .groupBy(qProjectTechnology.project.id)
                .having(qProjectTechnology.technologyStack.id.countDistinct().eq((long) technologyIds.size()))
                .fetch();

        if (projectIdsWithAllStacks.isEmpty()) {
            // 결과 없으면 false 조건 반환 (항상 false)
            return qProject.id.eq(-1L);
        }

        return qProject.id.in(projectIdsWithAllStacks);
    }

    private BooleanExpression eqBoardStatus(Boolean recruitmentStatus) {
        if (recruitmentStatus == null) {
            return null;
        }

        return qBoard.recruitmentStatus.eq(recruitmentStatus);
    }

    private BooleanExpression neBoardProjectStatus(ProjectStatus projectStatus) {
        if (projectStatus == null) {
            return null;
        }

        return qProject.status.ne(projectStatus);
    }
}
