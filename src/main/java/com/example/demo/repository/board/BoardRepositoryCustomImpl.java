package com.example.demo.repository.board;

import com.example.demo.constant.ProjectStatus;
import com.example.demo.dto.board.Response.BoardSearchResponseDto;
import com.example.demo.dto.boardposition.BoardPositionDetailResponseDto;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.position.response.PositionResponseDto;
import com.example.demo.dto.project.response.ProjectSearchResponseDto;
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
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final PositionRepository positionRepository;
    private final TechnologyStackRepository technologyStackRepository;

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
                        .orderBy(qBoard.createDate.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        List<BoardSearchResponseDto> boardSearchResponseDtos = new ArrayList<>();

        for (Board boardEntity : boards) {
            List<BoardPositionDetailResponseDto> boardPositions = new ArrayList<>();
            for (BoardPosition boardPosition : boardEntity.getPositions()) {
                BoardPositionDetailResponseDto boardPositionResponse =
                        BoardPositionDetailResponseDto.of(
                                boardPosition, PositionResponseDto.of(boardPosition.getPosition()));

                boardPositions.add(boardPositionResponse);
            }

            Project project = boardEntity.getProject();
            TrustGradeResponseDto projectTrustGradeResponseDto =
                    TrustGradeResponseDto.of(project.getTrustGrade());

            List<TechnologyStackInfoResponseDto> technologyStacks = new ArrayList<>();
            for (ProjectTechnology projectTechnology : project.getProjectTechnologies()) {
                TechnologyStack technologyStack = projectTechnology.getTechnologyStack();
                TechnologyStackInfoResponseDto technologyStackInfoResponseDto =
                        TechnologyStackInfoResponseDto.of(
                                technologyStack.getId(), technologyStack.getName());
                technologyStacks.add(technologyStackInfoResponseDto);
            }

            ProjectSearchResponseDto projectSearchResponseDto =
                    ProjectSearchResponseDto.of(
                            project, projectTrustGradeResponseDto, technologyStacks);

            User user = boardEntity.getUser();

            // 임시수정
            TrustGradeResponseDto userTrustGradeResponseDto =
                    TrustGradeResponseDto.of(user.getTrustScore().getTrustGrade());
            UserSearchResponseDto userSearchResponseDto =
                    UserSearchResponseDto.of(user, userTrustGradeResponseDto);

            boardSearchResponseDtos.add(
                    BoardSearchResponseDto.of(
                            boardEntity,
                            boardPositions,
                            projectSearchResponseDto,
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
        if (technologyIds != null && technologyIds.size() > 0) {
            List<TechnologyStack> technologyStackList = new ArrayList<>();
            for (Long technologyId : technologyIds) {
                TechnologyStack technologyStack =
                        technologyStackRepository
                                .findById(technologyId)
                                .orElseThrow(
                                        () ->
                                                TechnologyStackCustomException
                                                        .NOT_FOUND_TECHNOLOGY_STACK);
                technologyStackList.add(technologyStack);
            }

            return qProjectTechnology.technologyStack.in(technologyStackList);
        } else {
            return null;
        }
    }

    private BooleanExpression eqBoardStatus(Boolean recruitmentStatus) {
        if(recruitmentStatus == null) {
            return null;
        }

        return qBoard.recruitmentStatus.eq(recruitmentStatus);
    }

    private BooleanExpression neBoardProjectStatus(ProjectStatus projectStatus) {
        if(projectStatus == null) {
            return null;
        }

        return qProject.status.ne(projectStatus);
    }
}
