package com.example.demo.service.project;

import com.example.demo.constant.ProjectMemberAuth;
import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.constant.UserProjectHistoryStatus;
import com.example.demo.dto.boardposition.BoardPositionDetailResponseDto;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.position.response.PositionResponseDto;
import com.example.demo.dto.project.response.ProjectSummaryResponseDto;
import com.example.demo.dto.project.setting.request.ProjectSettingBoardUpdRequestDto;
import com.example.demo.dto.project.setting.request.ProjectSettingInfoUpdRequestDto;
import com.example.demo.dto.project.setting.response.ProjectSettingBoardResponseDto;
import com.example.demo.dto.project.setting.response.ProjectSettingInfoResponseDto;
import com.example.demo.dto.projectmember.ProjectMemberAuthDto;
import com.example.demo.dto.technology_stack.response.TechnologyStackInfoResponseDto;
import com.example.demo.global.exception.customexception.PageNationCustomException;
import com.example.demo.global.exception.customexception.ProjectCustomException;
import com.example.demo.global.exception.customexception.ProjectMemberCustomException;
import com.example.demo.global.log.PMLog;
import com.example.demo.model.board.Board;
import com.example.demo.model.board.BoardPosition;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.project.ProjectTechnology;
import com.example.demo.model.technology_stack.TechnologyStack;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserProjectHistory;
import com.example.demo.service.board.BoardPositionService;
import com.example.demo.service.board.BoardService;
import com.example.demo.service.milestone.MilestoneService;
import com.example.demo.service.position.PositionService;
import com.example.demo.service.technology_stack.TechnologyStackService;
import com.example.demo.service.user.UserProjectHistoryService;
import com.example.demo.service.user.UserService;
import com.example.demo.service.work.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.global.log.PMLog.PROJECT_SETTING;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectFacade {

    private final UserService userService;
    private final ProjectService projectService;
    private final ProjectMemberService projectMemberService;
    private final WorkService workService;
    private final MilestoneService milestoneService;
    private final UserProjectHistoryService userProjectHistoryService;
    private final BoardService boardService;
    private final BoardPositionService boardPositionService;
    private final PositionService positionService;
    private final TechnologyStackService technologyStackService;
    private final ProjectTechnologyService projectTechnologyService;


    /**
     * 현재 사용자의 참여중인 프로젝트 조회
     * @param userId
     * @param pageIndex
     * @param itemCount
     * @return
     */
    @Transactional(readOnly = true)
    public PaginationResponseDto getMyParticipatingProjects(Long userId, int pageIndex, int itemCount) {
        if (pageIndex < 0) {
            throw PageNationCustomException.INVALID_PAGE_NUMBER;
        }

        if (itemCount > 8) {
            throw PageNationCustomException.INVALID_PAGE_ITEM_COUNT;
        }

        User currentUser = userService.findById(userId);

        List<ProjectMember> projects =
                projectMemberService.getProjectMembersByUserAndStatus(currentUser, ProjectMemberStatus.PARTICIPATING);

        long totalPages = projects.size();

        List<Project> sortedProjects = projects
                .stream()
                .map(ProjectMember::getProject)
                .sorted(Comparator.comparing(Project::getStartDate).reversed())
                .collect(Collectors.toList());

        int startIndex = Math.min(pageIndex * itemCount, sortedProjects.size());
        int endIndex = Math.min(startIndex + itemCount, sortedProjects.size());
        List<Project> slicedProjects = new ArrayList<>(sortedProjects.subList(startIndex, endIndex));

        List<ProjectSettingInfoResponseDto> content = new ArrayList<>();
        for (Project project : slicedProjects) {
            content.add(getProjectSettingInfoResponseDto(project));
        }

        return PaginationResponseDto.of(content, totalPages);
    }


    /**
     * 프로젝트 종료
     * 해당 프로젝트 멤버의 새로운 사용자 프로젝트 이력 추가(프로젝트 완주 이력)
     * 해당 프로젝트와 관련된 업무, 마일스톤, 알림, 멤버, 기술스택 정보 삭제
     *
     * @param userId
     * @param projectId
     */
    @Transactional
    public void endProject(Long userId, Long projectId) {
        // 프로젝트 멤버 확인
        validateProjectMember(userId, projectId);

        // 프로젝트 설정 권한 확인
        validateProjectConfigAuth(userId, projectId);

        try{

            Project project = projectService.findById(projectId);
            for (ProjectMember projectMember : project.getProjectMembers()) {
                // 프로젝트 멤버 프로젝트 완주 이력 추가
                UserProjectHistory projectFinishHistory = UserProjectHistory.builder()
                        .project(project)
                        .user(projectMember.getUser())
                        .status(UserProjectHistoryStatus.PHIST_STAT_003)
                        .build();
                userProjectHistoryService.save(projectFinishHistory);
            }

            // 프로젝트 멤버 삭제
            project.removeProjectMembers();

            // 프로젝트 기술목록 삭제
            project.removeProjectTechnologies();

            // 프로젝트 모든 업무 삭제
            workService.deleteAllByProject(project);

            // 프로젝트 모든 마일스톤 삭제
            milestoneService.deleteAllByProject(project);

            // 프로젝트 status 종료로 변경
            project.endProject();

        }catch(Exception e){
            PMLog.e(PROJECT_SETTING, e.getMessage(), e);
            throw e;
        }

    }

    /**
     * 프로젝트 설정 - 프로젝트 정보 수정
     * @param userId
     * @param requestDto
     */
    public void updateProjectSettingInfo(Long userId, ProjectSettingInfoUpdRequestDto requestDto) {
        // validation
        validateProjectConfigAuth(userId, requestDto.getProjectId());

        if (requestDto.getAuthMap() == null || !requestDto.getAuthMap().isConfigYn()) {
            throw ProjectCustomException.ACCESS_NOT_ALLOWED;
        }

        Project project = projectService.findById(requestDto.getProjectId());

        // projectName, projectSubject, startDate, endDate 업데이트
        project.updateProject(
                requestDto.getProjectName(),
                requestDto.getProjectSubject(),
                requestDto.getStartDate(),
                requestDto.getEndDate()
        );

        // 프로젝트 기술 업데이트
        List<ProjectTechnology> projectTechnologyList = new ArrayList<>();
        for (Long technologyId : requestDto.getTechnologyIds()) {
            TechnologyStack technologyStack = technologyStackService.findById(technologyId);
            ProjectTechnology projectTechnology =
                    projectTechnologyService.getProjectTechnologyEntity(project, technologyStack);
            projectTechnologyList.add(projectTechnology);
        }
        project.changeProjectTechnologys(projectTechnologyList);
    }

    /**
     * 프로젝트 설정 - 게시글 정보 수정
     *
     * @param dto
     */
    public void updateProjectSettingBoard(Long userId, ProjectSettingBoardUpdRequestDto dto) {
        // 프로젝트 멤버인지 확인
        validateProjectMember(userId, dto.getProjectId());

        // 설정 수정 권한 확인
        if (dto.getAuthMap() == null || !dto.getAuthMap().isConfigYn()) {
            throw ProjectCustomException.NO_PERMISSION_TO_TASK;
        }

        Board board = boardService.findById(dto.getBoardId());

        // 게시글 정보 - 제목, 소개, 모집상태 수정
        if (!board.getTitle().equals(dto.getTitle())
                || !board.getContent().equals(dto.getContent())
                || !board.getContact().equals( dto.getContact())
                || board.isRecruitmentStatus() != dto.isRecruitmentStatus()) {
            board.updateProjectBoard(dto.getTitle(), dto.getContent(), dto.getContact(), dto.isRecruitmentStatus());
        }

        // 게시글 정보 - 작성자 수정
        if (!userId.equals(board.getUser().getId())) {
            User user = userService.findById(userId);
            board.updateProjectBoardUser(user);
        }

        // 게시글 정보 - 포지션 수정
        List<BoardPosition> boardPositions = board.getPositions();
        String positionIdStr = boardPositions.stream()
                .map(bp -> bp.getPosition().getId().toString())
                .collect(Collectors.joining(","));

        String dtoPostionIdStr = dto.getPositionIds().stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

        if (!positionIdStr.equals(dtoPostionIdStr)) {
            boardPositionService.deleteBoardPositionsByBoardId(dto.getBoardId());

            // 새 boardPosition 생성
            List<BoardPosition> boardPositionList = new ArrayList<>();
            for (Long positionId : dto.getPositionIds()) {
                Position position = positionService.findById(positionId);
                BoardPosition boardPosition =
                        boardPositionService.getBoardPositionEntity(board, position);
                boardPositionService.save(boardPosition);
            }
            board.setPositions(boardPositionList);
        }

    }

    /**
     * 프로젝트 세팅 - 프로젝트 정보 조회(이름,주제,시작/종료날짜,모집상태,기술스택)
     * @param projectId
     * @return
     */
    public ProjectSettingInfoResponseDto getProjectSettingInfo(Long projectId) {
        return getProjectSettingInfoResponseDto(projectId);
    }

    public ProjectSettingInfoResponseDto getProjectSettingInfoResponseDto(Long projectId) {
        Project project = projectService.findById(projectId);
        List<TechnologyStackInfoResponseDto> technologyStackInfoResponseDtos = new ArrayList<>();
        for (ProjectTechnology projectTechnology : project.getProjectTechnologies()) {
            TechnologyStack technologyStack = projectTechnology.getTechnologyStack();

            TechnologyStackInfoResponseDto technologyStackInfoResponseDto =
                    TechnologyStackInfoResponseDto.of(
                            technologyStack.getId(), technologyStack.getName());
            technologyStackInfoResponseDtos.add(technologyStackInfoResponseDto);
        }

        return ProjectSettingInfoResponseDto.of(project, technologyStackInfoResponseDtos);
    }

    public ProjectSettingInfoResponseDto getProjectSettingInfoResponseDto(Project project) {
        List<TechnologyStackInfoResponseDto> technologyStackInfoResponseDtos = new ArrayList<>();
        for (ProjectTechnology projectTechnology : project.getProjectTechnologies()) {
            TechnologyStack technologyStack = projectTechnology.getTechnologyStack();

            TechnologyStackInfoResponseDto technologyStackInfoResponseDto =
                    TechnologyStackInfoResponseDto.of(
                            technologyStack.getId(), technologyStack.getName());
            technologyStackInfoResponseDtos.add(technologyStackInfoResponseDto);
        }

        return ProjectSettingInfoResponseDto.of(project, technologyStackInfoResponseDtos);
    }

    /**
     * 프로젝트 세팅 - 게시글 정보 조회
     * @param userId
     * @param projectId
     * @return
     */
    public ProjectSettingBoardResponseDto getProjectSettingBoardInfo(Long userId, Long projectId) {
        validateProjectConfigAuth(userId, projectId);

        Board board = boardService.findByProjectId(projectId);

        List<BoardPositionDetailResponseDto> boardPositionDetailResponseDtos = new ArrayList<>();
        for (BoardPosition boardPosition : board.getPositions()) {
            PositionResponseDto positionResponseDto =
                    PositionResponseDto.of(boardPosition.getPosition());
            BoardPositionDetailResponseDto boardPositionDetailResponseDto =
                    BoardPositionDetailResponseDto.of(boardPosition, positionResponseDto);
            boardPositionDetailResponseDtos.add(boardPositionDetailResponseDto);
        }

        return ProjectSettingBoardResponseDto.of(board, boardPositionDetailResponseDtos);
    }

    /**
     * 현재 사용자의 프로젝트 멤버 권한 조회
     * @param userId
     * @param projectId
     * @return
     */
    public ProjectMemberAuthDto<ProjectMemberAuth> getCurrentUserProjectMemberAuth(Long userId, Long projectId) {
        ProjectMember projectMember = projectMemberService.findProjectMemberByPrIdAndUserId(projectId, userId);
        if (projectMember == null) {
            throw ProjectMemberCustomException.NOT_FOUND_PROJECT_MEMBER;
        }
        return ProjectMemberAuthDto.of(projectMember.getProjectMemberAuth());
    }

    public void validateProjectConfigAuth(Long userId, Long projectId) {
        ProjectMember projectMember = projectMemberService.findProjectMemberByPrIdAndUserId(projectId, userId);
        if (projectMember == null) throw ProjectCustomException.ACCESS_NOT_ALLOWED;

        ProjectMemberAuth projectMemberAuth = projectMember.getProjectMemberAuth();
        if (!projectMemberAuth.isConfigYn()) throw ProjectCustomException.ACCESS_NOT_ALLOWED;

    }

    public void validateProjectMember(Long userId, Long projectId) {
        ProjectMember projectMember = projectMemberService.findProjectMemberByPrIdAndUserId(projectId, userId);
        if (projectMember == null) {
            throw ProjectCustomException.ACCESS_NOT_ALLOWED;
        }
    }

    public ProjectSummaryResponseDto getProjectPublicInfo(Long projectId){
        Project project = projectService.findById(projectId);
        List<TechnologyStackInfoResponseDto> technologyStackInfoResponseDtos = new ArrayList<>();
        for (ProjectTechnology projectTechnology : project.getProjectTechnologies()) {
            TechnologyStack technologyStack = projectTechnology.getTechnologyStack();

            TechnologyStackInfoResponseDto technologyStackInfoResponseDto =
                    TechnologyStackInfoResponseDto.of(
                            technologyStack.getId(), technologyStack.getName());
            technologyStackInfoResponseDtos.add(technologyStackInfoResponseDto);
        }

        return ProjectSummaryResponseDto.of(project, technologyStackInfoResponseDtos);
    }
}
