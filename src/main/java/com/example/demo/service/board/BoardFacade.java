package com.example.demo.service.board;

import com.example.demo.constant.ProjectMemberAuth;
import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.dto.board.Response.BoardCreateResponseDto;
import com.example.demo.dto.board_project.request.BoardProjectCreateRequestDto;
import com.example.demo.dto.board_project.response.BoardProjectCreateResponseDto;
import com.example.demo.dto.project.response.ProjectCreateResponseDto;
import com.example.demo.model.board.Board;
import com.example.demo.model.board.BoardPosition;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.project.ProjectTechnology;
import com.example.demo.model.technology_stack.TechnologyStack;
import com.example.demo.model.trust_grade.TrustGrade;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserProjectHistory;
import com.example.demo.service.position.PositionService;
import com.example.demo.service.project.ProjectMemberService;
import com.example.demo.service.project.ProjectServiceImpl;
import com.example.demo.service.project.ProjectTechnologyService;
import com.example.demo.service.technology_stack.TechnologyStackService;
import com.example.demo.service.user.UserProjectHistoryService;
import com.example.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardFacade {
    private final BoardService boardService;
    private final UserService userService;
    private final ProjectServiceImpl projectServiceImpl;
    private final TechnologyStackService technologyStackService;
    private final ProjectTechnologyService projectTechnologyService;
    private final PositionService positionService;
    private final BoardPositionService boardPositionService;
    private final ProjectMemberService projectMemberService;
    private final UserProjectHistoryService userProjectHistoryService;

    /**
     * 게시글, 프로젝트 생성, 프로젝트 기술 생성, 프로젝트 멤버 생성, 사용자 이력 생성, 게시글-포지션 생성
     *
     * @param dto
     * @return
     */
    @Transactional
    public BoardProjectCreateResponseDto create(Long userId, BoardProjectCreateRequestDto dto) {
        User tempUser = userService.findById(userId);
        TrustGrade trustGrade = tempUser.getTrustScore().getTrustGrade();

        // project 생성
        Project project = dto.getProject().toProjectEntity(trustGrade, tempUser);
        Project savedProject = projectServiceImpl.save(project);

        // 프로젝트 기술 생성
        for (Long technolgoyId : dto.getProject().getTechnologyIds()) {
            TechnologyStack technologyStack = technologyStackService.findById(technolgoyId);
            ProjectTechnology projectTechnology =
                    projectTechnologyService.getProjectTechnologyEntity(
                            savedProject, technologyStack);
            projectTechnologyService.save(projectTechnology);
        }

        // board 생성
        Board board = dto.getBoard().toBoardEntity(savedProject, tempUser);
        Board savedBoard = boardService.save(board);

        // boardPosition 생성
        List<BoardPosition> boardPositionList = new ArrayList<>();
        for (Long positionId : dto.getBoard().getPositionIds()) {
            Position position = positionService.findById(positionId);

            BoardPosition boardPosition =
                    boardPositionService.getBoardPositionEntity(savedBoard, position);
            boardPositionService.save(boardPosition);
        }
        savedBoard.setPositions(boardPositionList);

        // 프로젝트멤버 크루권한으로 초기화
        ProjectMember projectMember =
                projectMemberService.toProjectMemberEntity(
                        project,
                        tempUser,
                        ProjectMemberAuth.PAUTH_1001,
                        ProjectMemberStatus.PARTICIPATING,
                        project.getUser().getPosition());
        projectMemberService.save(projectMember);

        // 사용자 프로젝트 이력 생성
        UserProjectHistory userProjectHistory =
                userProjectHistoryService.toUserProjectHistoryEntity(tempUser, savedProject);
        userProjectHistoryService.save(userProjectHistory);

        // response값 생성
        BoardCreateResponseDto boardCreateResponseDto = BoardCreateResponseDto.of(board);
        ProjectCreateResponseDto projectCreateResponseDto = ProjectCreateResponseDto.of(project);

        return new BoardProjectCreateResponseDto(boardCreateResponseDto, projectCreateResponseDto);
    }

    @Transactional
    public void deleteBoard(Long userId, Long boardId) {
        User tempUser = userService.findById(userId);
        Board board = boardService.findById(boardId);

        // 게시글 작성자 검증
        board.validationUser(tempUser);

        boardService.delete(board);
    }
}
