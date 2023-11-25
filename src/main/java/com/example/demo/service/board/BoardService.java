package com.example.demo.service.board;

import com.example.demo.dto.board.Request.BoardSearchRequestDto;
import com.example.demo.dto.board.response.BoardSearchResponseDto;
import com.example.demo.model.board.Board;
import com.example.demo.model.board.QBoard;
import com.example.demo.model.board.QBoardPosition;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.QProject;
import com.example.demo.model.user.QUser;
import com.example.demo.repository.board.BoardPositionRepository;
import com.example.demo.repository.board.BoardRepository;
import com.example.demo.repository.position.PositionRepository;
import com.example.demo.repository.project.ProjectMemberAuthRepository;
import com.example.demo.repository.project.ProjectMemberRepository;
import com.example.demo.repository.project.ProjectRepository;
import com.example.demo.repository.trust_grade.TrustGradeRepository;
import com.example.demo.repository.user.UserProjectHistoryRepository;
import com.example.demo.repository.user.UserRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class BoardService {

    private final PositionRepository positionRepository;
    /**
     * 게시글 목록 검색
     *
     * @param dto
     * @return List<BoardSearchResponseDto>
     */
    @Transactional(readOnly = true)
    public List<BoardSearchResponseDto> search(BoardSearchRequestDto dto) {
        QBoard board = QBoard.board;
        QProject project = QProject.project;
        QBoardPosition boardPosition = QBoardPosition.boardPosition;
        QUser user = QUser.user;

        BooleanBuilder builder = new BooleanBuilder();

        if (dto.getKeyWord() != null && !dto.getKeyWord().isEmpty()) {
            builder.or(board.title.eq(dto.getKeyWord()));
            builder.or(board.content.eq(dto.getKeyWord()));
        }

        if (dto.getPositionIds().size() > 0) {

            List<Position> positionList = new ArrayList<>();
            for (Long positionId : dto.getPositionIds()) {
                Position position =
                        positionRepository
                                .findById(positionId)
                                .orElseThrow(() -> PositionCustomException.NOT_FOUND_POSITION);
                positionList.add(position);
            }

            builder.or(boardPosition.position.in(positionList));
        }

        List<Board> boards =
                queryFactory
                        .selectDistinct(board)
                        .from(board)
                        .join(board.project, project)
                        .join(board.positions, boardPosition).fetchJoin()
                        .where(builder)
                        .fetch();

        List<BoardSearchResponseDto> boardSearchResponseDtos = new ArrayList<>();

        for (Board boardEntity : boards) {
            boardSearchResponseDtos.add(BoardSearchResponseDto.of(boardEntity));
        }

        return boardSearchResponseDtos;
    }
}
