package com.example.demo.controller.board;

import com.example.demo.dto.board.Response.BoardTotalDetailResponseDto;
import com.example.demo.dto.board_project.request.BoardProjectCreateRequestDto;
import com.example.demo.dto.board_project.response.BoardProjectCreateResponseDto;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.common.ResponseDto;
import com.example.demo.security.custom.PrincipalDetails;
import com.example.demo.service.board.BoardFacade;
import com.example.demo.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;
    private final BoardFacade boardFacade;

    @GetMapping("/search/public")
    public ResponseEntity<ResponseDto<?>> search(
            @RequestParam(value = "positionId", required = false) Long positionId,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "technologyIds", required = false) List<Long> technologyIds,
            @RequestParam("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 8);
        PaginationResponseDto result =
                boardService.search(positionId, keyword, technologyIds, pageable);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }

    @GetMapping("/{boardId}/public")
    public ResponseEntity<ResponseDto<?>> getDetail(@PathVariable("boardId") Long boardId) {
        BoardTotalDetailResponseDto result = boardService.getDetail(boardId);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDto<?>> create(
            @AuthenticationPrincipal PrincipalDetails user,
            @RequestBody BoardProjectCreateRequestDto requestDto) {
        BoardProjectCreateResponseDto result = boardFacade.create(user.getId(), requestDto);
        return new ResponseEntity<>(ResponseDto.success("프로젝트와 모집 게시글을 생성했습니다.", result), HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<ResponseDto<?>> delete(
            @AuthenticationPrincipal PrincipalDetails user, @PathVariable("boardId") Long boardId) {
        boardFacade.deleteBoard(user.getId(), boardId);
        return new ResponseEntity<>(ResponseDto.success("게시글을 삭제했습니다.", null), HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{boardId}/recruitment-status")
    public ResponseEntity<ResponseDto<?>> updateBoardRecruitmentStatus(
            @PathVariable Long boardId, @AuthenticationPrincipal PrincipalDetails user) {
        boardService.updateRecruitmentStatus(boardId, user.getId());
        return new ResponseEntity<>(ResponseDto.success("게시글 모집상태 변경이 완료되었습니다."), HttpStatus.OK);
    }
}
