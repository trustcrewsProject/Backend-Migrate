package com.example.demo.service.board;

import com.example.demo.constant.ProjectStatus;
import com.example.demo.dto.board.Response.BoardDetailResponseDto;
import com.example.demo.dto.board.Response.BoardTotalDetailResponseDto;
import com.example.demo.dto.boardposition.BoardPositionDetailResponseDto;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.position.response.PositionResponseDto;
import com.example.demo.dto.project.response.ProjectDetailResponseDto;
import com.example.demo.dto.technology_stack.response.TechnologyStackInfoResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeResponseDto;
import com.example.demo.dto.user.response.UserBoardDetailResponseDto;
import com.example.demo.dto.user.response.UserProjectResponseDto;
import com.example.demo.global.exception.customexception.BoardCustomException;
import com.example.demo.model.board.Board;
import com.example.demo.model.board.BoardPosition;
import com.example.demo.model.project.ProjectTechnology;
import com.example.demo.model.technology_stack.TechnologyStack;
import com.example.demo.repository.board.BoardRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    /**
     * 게시글 목록 검색
     *
     * @param
     * @return PaginationResponseDto
     */
    @Transactional(readOnly = true)
    public PaginationResponseDto search(
            Long positionId, String keyword, List<Long> technologyIds, Pageable pageable) {
        return boardRepository.getBoardSearchPage(positionId, keyword, technologyIds, false, ProjectStatus.FINISH, pageable);
    }

    public Board findById(Long boardId) {
        return boardRepository
                .findById(boardId)
                .orElseThrow(() -> BoardCustomException.NOT_FOUND_BOARD);
    }

    public Board save(Board board) {
        return boardRepository.save(board);
    }

    /**
     * 게시글 상세 조회
     *
     * @param boardId
     * @return
     */
    public BoardTotalDetailResponseDto getDetail(Long boardId) {
        Board board = findById(boardId);

        // boardDetailResponseDto 생성
        UserBoardDetailResponseDto userBoardDetailResponseDto =
                UserBoardDetailResponseDto.of(board.getUser());

        List<BoardPositionDetailResponseDto> boardPositionDetailResponseDtos = new ArrayList<>();
        for (BoardPosition boardPosition : board.getPositions()) {
            PositionResponseDto positionResponseDto =
                    PositionResponseDto.of(boardPosition.getPosition());
            BoardPositionDetailResponseDto boardPositionDetailResponseDto =
                    BoardPositionDetailResponseDto.of(boardPosition, positionResponseDto);
            boardPositionDetailResponseDtos.add(boardPositionDetailResponseDto);
        }
        BoardDetailResponseDto boardDetailResponseDto =
                BoardDetailResponseDto.of(
                        board, userBoardDetailResponseDto, boardPositionDetailResponseDtos);

        // ProjectDetailResponseDto 부분
        TrustGradeResponseDto trustGradeDto =
                TrustGradeResponseDto.of(board.getProject().getTrustGrade());
        UserProjectResponseDto userProjectResponseDto =
                UserProjectResponseDto.of(board.getProject());

        // 기술 스택 부분
        List<TechnologyStackInfoResponseDto> technologyStackInfoResponseDtos = new ArrayList<>();
        for (ProjectTechnology projectTechnology : board.getProject().getProjectTechnologies()) {
            TechnologyStack technologyStack = projectTechnology.getTechnologyStack();

            TechnologyStackInfoResponseDto technologyStackInfoResponseDto =
                    TechnologyStackInfoResponseDto.of(
                            technologyStack.getId(), technologyStack.getName());
            technologyStackInfoResponseDtos.add(technologyStackInfoResponseDto);
        }
        ProjectDetailResponseDto projectDetailResponseDto =
                ProjectDetailResponseDto.of(
                        board.getProject(),
                        trustGradeDto,
                        userProjectResponseDto,
                        technologyStackInfoResponseDtos);

        // boardPageView 증가
        board.updatePageView();

        return BoardTotalDetailResponseDto.of(boardDetailResponseDto, projectDetailResponseDto);
    }

    /**
     * 게시글 삭제
     *
     * @param board
     */
    public void delete(Board board) {
        boardRepository.delete(board);
    }

    /**
     * 게시글 모집상태 변경, 모집중 -> 모집완료 or 모집완료 -> 모집중
     * @param boardId
     * @param userId
     */
    @Override
    public void updateRecruitmentStatus(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> BoardCustomException.NOT_FOUND_BOARD);

        // 요청한 사용자와 게시글의 작성자가 다른 경우, 예외처리
        if(!userId.equals(board.getUser().getId())) {
            throw BoardCustomException.NO_PERMISSION_TO_EDIT_OR_DELETE;
        }

        // 게시글 모집상태가 모집중(false)인 경우, 모집완료(true)로 수정
        if(!board.isRecruitmentStatus()) {
            board.updateRecruitmentStatus(true);
            return;
        }

        // 게시글 모집상태가 완료(true)인 경우, 모집중(false)으로 수정
        board.updateRecruitmentStatus(false);
    }
}
