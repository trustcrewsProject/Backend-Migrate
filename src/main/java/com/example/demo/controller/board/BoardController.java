package com.example.demo.controller.board;

import com.example.demo.dto.board.response.BoardSearchPaginationResponseDto;
import com.example.demo.dto.board.response.BoardTotalDetailResponseDto;
import com.example.demo.dto.board_project.request.BoardProjectCreateRequestDto;
import com.example.demo.dto.board_project.request.BoardProjectUpdateRequestDto;
import com.example.demo.dto.board_project.response.BoardProjectCreateResponseDto;
import com.example.demo.dto.board_project.response.BoardProjectUpdateResponseDto;
import com.example.demo.dto.common.ResponseDto;
import com.example.demo.security.custom.PrincipalDetails;
import com.example.demo.service.board.BoardFacade;
import com.example.demo.service.board.BoardService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
        BoardSearchPaginationResponseDto result =
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
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<ResponseDto<?>> update(
            @AuthenticationPrincipal PrincipalDetails user,
            @PathVariable("boardId") Long boardId,
            @RequestBody BoardProjectUpdateRequestDto requestDto) {
        try {
            BoardProjectUpdateResponseDto result =
                    boardFacade.update(user.getId(), boardId, requestDto);
            return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ResponseDto.fail(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<ResponseDto<?>> delete(
            @AuthenticationPrincipal PrincipalDetails user, @PathVariable("boardId") Long boardId) {
        boardFacade.deleteBoard(user.getId(), boardId);
        return new ResponseEntity<>(ResponseDto.success("success", null), HttpStatus.NO_CONTENT);
    }
}
