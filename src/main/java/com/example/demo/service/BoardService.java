package com.example.demo.service;

import com.example.demo.dto.board.BoardCreateResponseDto;
import com.example.demo.dto.board.BoardUpdateResponseDto;
import com.example.demo.dto.boardProject.BoardProjectCreateRequestDto;
import com.example.demo.dto.boardProject.BoardProjectCreateResponseDto;
import com.example.demo.dto.boardProject.BoardProjectUpdateResponseDto;
import com.example.demo.dto.boardProject.BoardProjectUpdateRequestDto;
import com.example.demo.dto.project.ProjectCreateResponseDto;
import com.example.demo.dto.project.ProjectUpdateResponseDto;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.constant.ProjectStatus.RECRUITING;

@Service
public class BoardService {

    @Autowired PositionRepository positionRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    BoardPositionRepository boardPositionRepository;

    public BoardProjectCreateResponseDto create(BoardProjectCreateRequestDto dto) throws Exception{
        Project project = new Project();
        User tempUser = userRepository.findById(1L).get(); // 나중에 Security로 고쳐야 함.

        //project 생성
        project.setName(dto.getProject().getProjectName());
        project.setSubject(dto.getProject().getProjectSubject());
        project.setTrustGrade(dto.getProject().getProjectTrust());
        project.setUser(tempUser);
        project.setStatus(RECRUITING);
        project.setCrewNumber(dto.getProject().getProjectCrewNumber());
        project.setStartDate(dto.getProject().getProjectStartDate());
        project.setEndDate(dto.getProject().getProjectEndDate());
        Project savedProject = projectRepository.save(project);


        //board 생성
        Board board = new Board();
        board.setTitle(dto.getBoard().getBoardTitle());
        board.setContent(dto.getBoard().getBoardContent());
        board.setProject(savedProject);
        board.setUser(tempUser);
        board.setContact(dto.getBoard().getBoardContent());
        board.setPosition(null);
        Board savedBoard = boardRepository.save(board);

        //boardPosition 생성
        for (Position position : dto.getBoard().getBoardPositions()) {
            BoardPosition boardPosition = new BoardPosition(savedBoard, position);
            boardPositionRepository.save(boardPosition);
        }

        //response값 생성
        BoardCreateResponseDto boardCreateResponseDto = BoardCreateResponseDto.of(board);
        ProjectCreateResponseDto projectCreateResponseDto = ProjectCreateResponseDto.of(project);

        return new BoardProjectCreateResponseDto(boardCreateResponseDto, projectCreateResponseDto);
    }

    public BoardProjectUpdateResponseDto update(BoardProjectUpdateRequestDto dto) throws Exception{
        Project project = projectRepository.findById(dto.getProject().getProjectId()).get();
        User tempUser = userRepository.findById(1L).get(); // 나중에 Security로 고쳐야 함.

        //project 생성
        project.setName(dto.getProject().getProjectName());
        project.setSubject(dto.getProject().getProjectSubject());
        project.setTrustGrade(dto.getProject().getProjectTrust());
        project.setCrewNumber(dto.getProject().getProjectCrewNumber());
        project.setStartDate(dto.getProject().getProjectStartDate());
        project.setEndDate(dto.getProject().getProjectEndDate());
        Project savedProject = projectRepository.save(project);

        //board 생성
        Board board = boardRepository.findById(dto.getBoard().getBoardId()).get();
        board.setTitle(dto.getBoard().getBoardTitle());
        board.setContent(dto.getBoard().getBoardContent());
        board.setContact(dto.getBoard().getBoardContact());

        //position 받아서 다시 보드-포지션 연결
        List<Position> positions = dto.getBoard().getBoardPositions();
        List<BoardPosition> boardPositionList = new ArrayList<>();
        for (Position position : positions) {
            BoardPosition boardPosition = new BoardPosition(board, position);
            boardPositionRepository.save(boardPosition);
            boardPositionList.add(boardPosition);
        }

        //바뀌면서 원래 있던 것이 사라질것임.
        board.setPosition(boardPositionList);
        Board savedBoard = boardRepository.save(board);

        //response값 생성
        BoardUpdateResponseDto boardUpdateResponseDto = BoardUpdateResponseDto.of(board);
        ProjectUpdateResponseDto projectUpdateResponseDto = ProjectUpdateResponseDto.of(project);

        return new BoardProjectUpdateResponseDto(boardUpdateResponseDto, projectUpdateResponseDto);
    }
}
