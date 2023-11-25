package com.example.demo.service.board;

import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.constant.UserProjectHistoryStatus;
import com.example.demo.dto.board.request.BoardSearchRequestDto;
import com.example.demo.dto.board.response.BoardCreateResponseDto;
import com.example.demo.dto.board.response.BoardSearchResponseDto;
import com.example.demo.dto.board_project.request.BoardProjectCreateRequestDto;
import com.example.demo.dto.board_project.response.BoardProjectCreateResponseDto;
import com.example.demo.dto.project.response.ProjectCreateResponseDto;
import com.example.demo.global.exception.customexception.PositionCustomException;
import com.example.demo.model.board.Board;
import com.example.demo.model.board.BoardPosition;
import com.example.demo.model.board.QBoard;
import com.example.demo.model.board.QBoardPosition;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.project.ProjectMemberAuth;
import com.example.demo.model.project.QProject;
import com.example.demo.model.trust_grade.TrustGrade;
import com.example.demo.model.user.QUser;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserProjectHistory;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.constant.ProjectStatus.RECRUITING;

@Transactional
@Service
@AllArgsConstructor
public class BoardService {

    private final PositionRepository positionRepository;

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;

    private final BoardPositionRepository boardPositionRepository;

    private final TrustGradeRepository trustGradeRepository;
    private final ProjectMemberAuthRepository projectMemberAuthRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final UserProjectHistoryRepository userProjectHistoryRepository;

    /**
     * 게시글 목록 검색
     *
     * @param dto
     * @return List<BoardSearchResponseDto>
     */
    @Transactional(readOnly = true)
    public Page<BoardSearchResponseDto> search(BoardSearchRequestDto dto, Pageable pageable) {
       return boardRepository.getBoardSearchPage(dto, pageable);
    }

    /**
     * 게시글, 프로젝트 생성 , 프로젝트 멤버 생성, 사용자 이력 생성, 게시글-포지션 생성
     * @param dto
     * @return
     */
    public BoardProjectCreateResponseDto create(BoardProjectCreateRequestDto dto) {
        User tempUser =
                userRepository
                        .findById(1L)
                        .orElseThrow(
                                () -> UserCustomException.NOT_FOUND_USER); // 나중에 Security로 고쳐야 함.

        TrustGrade trustGrade =
                trustGradeRepository
                        .findById(dto.getProject().getTrustGradeId())
                        .orElseThrow(() -> TrustGradeCustomException.NOT_FOUND_TRUST_GRADE);

        // project 생성
        Project project =
                Project.builder()
                        .name(dto.getProject().getName())
                        .subject(dto.getProject().getSubject())
                        .trustGrade(trustGrade)
                        .user(tempUser)
                        .status(RECRUITING)
                        .crewNumber(dto.getProject().getCrewNumber())
                        .startDate(dto.getProject().getStartDate())
                        .endDate(dto.getProject().getEndDate())
                        .build();

        Project savedProject = projectRepository.save(project);

        // board 생성
        Board board =
                Board.builder()
                        .title(dto.getBoard().getTitle())
                        .content(dto.getBoard().getContent())
                        .project(savedProject)
                        .user(tempUser)
                        .contact(dto.getBoard().getContent())
                        .build();

        Board savedBoard = boardRepository.save(board);

        // boardPosition 생성
        List<BoardPosition> boardPositionList = new ArrayList<>();
        for (Long positionId : dto.getBoard().getPositionIds()) {
            Position position =
                    positionRepository
                            .findById(positionId)
                            .orElseThrow(() -> PositionCustomException.NOT_FOUND_POSITION);
            BoardPosition boardPosition = new BoardPosition(savedBoard, position);
            boardPositionRepository.save(boardPosition);
        }
        savedBoard.setPositions(boardPositionList);

        ProjectMemberAuth projectMemberAuth = projectMemberAuthRepository.findById(1L).orElseThrow(() -> ProjectMemberAuthCustomException.NOT_FOUND_PROJECT_MEMBER_AUTH);
        ProjectMember projectMember = ProjectMember.builder()
                .project(savedProject)
                .user(tempUser)
                .projectMemberAuth(projectMemberAuth)
                .status(ProjectMemberStatus.PARTICIPATING)
                .position(savedProject.getUser().getPosition())
                .build();

        projectMemberRepository.save(projectMember);

        UserProjectHistory userProjectHistory = UserProjectHistory.builder()
                .user(tempUser)
                .project(savedProject)
                .startDate(LocalDateTime.now())
                .endDate(savedProject.getEndDate())
                .status(UserProjectHistoryStatus.PARTICIPATING)
                .build();

        userProjectHistoryRepository.save(userProjectHistory);

        // response값 생성
        BoardCreateResponseDto boardCreateResponseDto = BoardCreateResponseDto.of(board);
        ProjectCreateResponseDto projectCreateResponseDto = ProjectCreateResponseDto.of(project);

        return new BoardProjectCreateResponseDto(boardCreateResponseDto, projectCreateResponseDto);
    }
}
