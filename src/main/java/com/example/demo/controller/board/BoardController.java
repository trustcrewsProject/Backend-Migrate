package com.example.demo.controller.board;

import com.example.demo.dto.board.request.BoardSearchRequestDto;
import com.example.demo.dto.board.response.BoardSearchResponseDto;
import com.example.demo.dto.board.response.BoardTotalDetailResponseDto;
import com.example.demo.dto.common.ResponseDto;
import com.example.demo.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping(value = {"/search", "/search/{page}"})
    public ResponseEntity<ResponseDto<?>> get(@RequestBody BoardSearchRequestDto dto, @PathVariable("page")Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.isPresent()? page.get() : 0 , 5);
        Page<BoardSearchResponseDto> result = boardService.search(dto, pageable);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ResponseDto<?>> getDetail(@PathVariable("boardId") Long boardId) {
        BoardTotalDetailResponseDto result = boardService.getDetail(boardId);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }
}
