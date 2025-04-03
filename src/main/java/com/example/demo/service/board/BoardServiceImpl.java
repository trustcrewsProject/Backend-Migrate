package com.example.demo.service.board;

import com.example.demo.constant.ProjectStatus;
import com.example.demo.dto.board.Response.BoardDetailResponseDto;
import com.example.demo.dto.boardposition.BoardPositionDetailResponseDto;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.position.response.PositionResponseDto;
import com.example.demo.dto.user.response.UserBoardDetailResponseDto;
import com.example.demo.global.exception.customexception.BoardCustomException;
import com.example.demo.model.board.Board;
import com.example.demo.model.board.BoardPosition;
import com.example.demo.model.user.User;
import com.example.demo.repository.board.BoardRepository;
import com.example.demo.service.file.AwsS3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final AwsS3FileService awsS3FileService;

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
    public BoardDetailResponseDto getDetail(Long boardId) {
        // boardPageView 증가 - Hibernate가 pageView 필드 변경을 감지하지 못하도록 직접 db반영
        //        boardRepository.increasePageView(boardId);

        Board board = findById(boardId);
        // * ============= 게시글 상세 정보 ============= *
        User user = board.getUser();
        UserBoardDetailResponseDto userBoardDetailResponseDto = UserBoardDetailResponseDto.of(user,awsS3FileService.generatePreSignedUrl(user.getProfileImgSrc()));

        // 게시글 상세 - 모집 포지션
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

        return boardDetailResponseDto;
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
     *
     * @param boardId
     * @param userId
     */
    @Override
    public void updateRecruitmentStatus(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> BoardCustomException.NOT_FOUND_BOARD);

        // 요청한 사용자와 게시글의 작성자가 다른 경우, 예외처리
        if (!userId.equals(board.getUser().getId())) {
            throw BoardCustomException.NO_PERMISSION_TO_EDIT_OR_DELETE;
        }

        // 게시글 모집상태가 모집중(false)인 경우, 모집완료(true)로 수정
        if (!board.isRecruitmentStatus()) {
            board.updateRecruitmentStatus(true);
            return;
        }

        // 게시글 모집상태가 완료(true)인 경우, 모집중(false)으로 수정
        board.updateRecruitmentStatus(false);
    }

    @Override
    public Board findByProjectId(Long projectId) {
      return  boardRepository.findByProjectId(projectId);
    }

}
