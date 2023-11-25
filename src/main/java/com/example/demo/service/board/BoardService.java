package com.example.demo.service.board;

import com.example.demo.dto.board.request.BoardSearchRequestDto;
import com.example.demo.dto.board.response.BoardSearchResponseDto;
import com.example.demo.global.exception.customexception.PositionCustomException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class BoardService {

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
}
