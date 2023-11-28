package com.example.demo.service.board;

import com.example.demo.dto.board.request.BoardSearchRequestDto;
import com.example.demo.dto.board.response.*;
import com.example.demo.dto.boardposition.BoardPositionDetailResponseDto;
import com.example.demo.dto.position.response.PositionResponseDto;
import com.example.demo.dto.project.response.ProjectDetailResponseDto;
import com.example.demo.dto.technology_stack.response.TechnologyStackInfoResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeResponseDto;
import com.example.demo.dto.user.response.UserBoardDetailResponseDto;
import com.example.demo.dto.user.response.UserProjectResponseDto;
import com.example.demo.global.exception.customexception.*;
import com.example.demo.model.board.Board;
import com.example.demo.model.board.BoardPosition;
import com.example.demo.model.project.ProjectTechnology;
import com.example.demo.model.technology_stack.TechnologyStack;
import com.example.demo.repository.board.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

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

    public Board findBoardById(Long boardId){
        return boardRepository.findById(boardId).orElseThrow(() -> BoardCustomException.NOT_FOUND_BOARD);
    }

    public Board save(Board board){
        return boardRepository.save(board);
    }

    /**
     * 게시글 상세 조회
     * @param boardId
     * @return
     */
    public BoardTotalDetailResponseDto getDetail(Long boardId) {
        Board board = findBoardById(boardId);

        //boardDetailResponseDto 생성
        UserBoardDetailResponseDto userBoardDetailResponseDto = UserBoardDetailResponseDto.of(board.getUser());

        List<BoardPositionDetailResponseDto> boardPositionDetailResponseDtos = new ArrayList<>();
        for (BoardPosition boardPosition : board.getPositions()) {
            PositionResponseDto positionResponseDto = PositionResponseDto.of(boardPosition.getPosition());
            BoardPositionDetailResponseDto boardPositionDetailResponseDto = BoardPositionDetailResponseDto.of(boardPosition, positionResponseDto);
            boardPositionDetailResponseDtos.add(boardPositionDetailResponseDto);
        }
        BoardDetailResponseDto boardDetailResponseDto = BoardDetailResponseDto.of(board, userBoardDetailResponseDto, boardPositionDetailResponseDtos);

        // ProjectDetailResponseDto 부분
        TrustGradeResponseDto trustGradeDto = TrustGradeResponseDto.of(board.getProject().getTrustGrade());
        UserProjectResponseDto userProjectResponseDto = UserProjectResponseDto.of(board.getProject());

        //기술 스택 부분
        List<TechnologyStackInfoResponseDto> technologyStackInfoResponseDtos = new ArrayList<>();
        for (ProjectTechnology projectTechnology : board.getProject().getProjectTechnologies()) {
            TechnologyStack technologyStack = projectTechnology.getTechnologyStack();

            TechnologyStackInfoResponseDto technologyStackInfoResponseDto = TechnologyStackInfoResponseDto.of(technologyStack.getId(), technologyStack.getName());
            technologyStackInfoResponseDtos.add(technologyStackInfoResponseDto);
        }
        ProjectDetailResponseDto projectDetailResponseDto = ProjectDetailResponseDto.of(board.getProject(), trustGradeDto, userProjectResponseDto, technologyStackInfoResponseDtos);


        return BoardTotalDetailResponseDto.of(boardDetailResponseDto, projectDetailResponseDto);
    }

    /**
     * 게시글, 프로젝트 생성, 프로젝트 기술 생성, 프로젝트 멤버 생성, 사용자 이력 생성, 게시글-포지션 생성
     * @param dto
     * @return
     */
//    public BoardProjectCreateResponseDto create(BoardProjectCreateRequestDto dto) {
//
//        //신뢰등급 설정
//        TrustGrade trustGrade =
//                trustGradeRepository
//                        .findById(dto.getProject().getTrustGradeId())
//                        .orElseThrow(() -> TrustGradeCustomException.NOT_FOUND_TRUST_GRADE);
//
//        // project 생성
//        Project project =
//                Project.builder()
//                        .name(dto.getProject().getName())
//                        .subject(dto.getProject().getSubject())
//                        .trustGrade(trustGrade)
//                        .user(tempUser)
//                        .status(RECRUITING)
//                        .crewNumber(dto.getProject().getCrewNumber())
//                        .startDate(dto.getProject().getStartDate())
//                        .endDate(dto.getProject().getEndDate())
//                        .build();
//
//        Project savedProject = projectRepository.save(project);
//
//
//        //프로젝트 기술 생성
//        for (Long technolgoyId : dto.getProject().getTechnologyIds()) {
//            TechnologyStack technologyStack = technologyStackRepository.findById(technolgoyId).orElseThrow(() -> TechnologyStackCustomException.NOT_FOUND_TECHNOLOGY_STACK);
//            ProjectTechnology projectTechnology = ProjectTechnology.builder()
//                    .project(savedProject)
//                    .technologyStack(technologyStack)
//                    .build();
//
//            projectTechnologyRepository.save(projectTechnology);
//        }
//
//        // board 생성
//        Board board =
//                Board.builder()
//                        .title(dto.getBoard().getTitle())
//                        .content(dto.getBoard().getContent())
//                        .project(savedProject)
//                        .user(tempUser)
//                        .contact(dto.getBoard().getContact())
//                        .build();
//
//        Board savedBoard = boardRepository.save(board);
//
//        // boardPosition 생성
//        List<BoardPosition> boardPositionList = new ArrayList<>();
//        for (Long positionId : dto.getBoard().getPositionIds()) {
//            Position position =
//                    positionRepository
//                            .findById(positionId)
//                            .orElseThrow(() -> PositionCustomException.NOT_FOUND_POSITION);
//            BoardPosition boardPosition = new BoardPosition(savedBoard, position);
//            boardPositionRepository.save(boardPosition);
//        }
//        savedBoard.setPositions(boardPositionList);
//
//        //프로젝트 멤버 권한 일반인으로 설정
//        //프로젝트 멤버 생성
//        ProjectMemberAuth projectMemberAuth = projectMemberAuthRepository.findById(1L).orElseThrow(() -> ProjectMemberAuthCustomException.NOT_FOUND_PROJECT_MEMBER_AUTH);
//        ProjectMember projectMember = ProjectMember.builder()
//                .project(savedProject)
//                .user(tempUser)
//                .projectMemberAuth(projectMemberAuth)
//                .status(ProjectMemberStatus.PARTICIPATING)
//                .position(savedProject.getUser().getPosition())
//                .build();
//
//        projectMemberRepository.save(projectMember);
//
//        //사용자 프로젝트 이력 생성
//        UserProjectHistory userProjectHistory = UserProjectHistory.builder()
//                .user(tempUser)
//                .project(savedProject)
//                .startDate(LocalDateTime.now())
//                .endDate(savedProject.getEndDate())
//                .status(UserProjectHistoryStatus.PARTICIPATING)
//                .build();
//
//        userProjectHistoryRepository.save(userProjectHistory);
//
//        // response값 생성
//        BoardCreateResponseDto boardCreateResponseDto = BoardCreateResponseDto.of(board);
//        ProjectCreateResponseDto projectCreateResponseDto = ProjectCreateResponseDto.of(project);
//
//        return new BoardProjectCreateResponseDto(boardCreateResponseDto, projectCreateResponseDto);
//    }

    /**
     * 게시글, 프로젝트 업데이트
     * 게시글, 프로젝트, 프로젝트 기술, 게시글-포지션
     * TODO : 현재 유저가 업데이트 하도록 변경
     * @param dto
     * @return
     */
//    public BoardProjectUpdateResponseDto update(Long boardId, BoardProjectUpdateRequestDto dto) throws Exception{
//        Board board = boardRepository.findById(boardId).orElseThrow(() -> BoardCustomException.NOT_FOUND_BOARD);
//        Project project = board.getProject();
//        User tempUser = userRepository.findById(1L).get(); // 나중에 Security로 고쳐야 함.
//
//        TrustGrade trustGrade =
//                trustGradeRepository
//                        .findById(dto.getProject().getTrustGradeId())
//                        .orElseThrow(() -> TrustGradeCustomException.NOT_FOUND_TRUST_GRADE);
//
//        // project 업데이트
//        project.updateProject(dto.getProject(), trustGrade);
//
//        //board 업데이트
//        board.updateBoard(dto.getBoard());
//
//        //프로젝트 기술 업데이트
//        List<ProjectTechnology> projectTechnologyList = new ArrayList<>();
//        for (Long technologyId : dto.getProject().getTechnologyIds()) {
//            TechnologyStack technologyStack = technologyStackRepository.findById(technologyId).orElseThrow(() -> TechnologyStackCustomException.NOT_FOUND_TECHNOLOGY_STACK);
//            ProjectTechnology projectTechnology = ProjectTechnology.builder()
//                    .project(project)
//                    .technologyStack(technologyStack)
//                    .build();
//
//            projectTechnologyList.add(projectTechnology);
//        }
//        project.changeProjectTechnologys(projectTechnologyList);
//
//        // position 받아서 다시 게시글-포지션 연결
//        List<BoardPosition> boardPositionList = new ArrayList<>();
//        for (Long positionId : dto.getBoard().getPositionIds()) {
//            Position position = positionRepository
//                            .findById(positionId)
//                            .orElseThrow(() -> PositionCustomException.NOT_FOUND_POSITION);
//            BoardPosition boardPosition = new BoardPosition(board, position);
//            boardPositionList.add(boardPosition);
//        }
//        board.setPositions(boardPositionList);
//
//
//        // response값 생성
//        BoardUpdateResponseDto boardUpdateResponseDto = BoardUpdateResponseDto.of(board);
//        ProjectUpdateResponseDto projectUpdateResponseDto = ProjectUpdateResponseDto.of(project);
//
//        return new BoardProjectUpdateResponseDto(boardUpdateResponseDto, projectUpdateResponseDto);
//    }

    /**
     * 게시글 삭제
     *
     * @param boardId
     */
//    public void delete(Long boardId) {
//        Board board = boardRepository.findById(boardId).orElseThrow(() -> BoardCustomException.NOT_FOUND_BOARD);
//        boardRepository.delete(board);
//    }
}
