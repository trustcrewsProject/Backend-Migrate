package com.example.demo.controller.board;

import com.example.demo.dto.board.request.BoardSearchRequestDto;
import com.example.demo.dto.board.response.BoardSearchResponseDto;
import com.example.demo.dto.board_project.request.BoardProjectCreateRequestDto;
import com.example.demo.dto.board_project.response.BoardProjectCreateResponseDto;
import com.example.demo.dto.common.ResponseDto;

import com.example.demo.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/search")
    public ResponseEntity<ResponseDto<?>> get(@RequestBody BoardSearchRequestDto dto) {
        List<BoardSearchResponseDto> result = boardService.search(dto);
        return new ResponseEntity<>(new ResponseDto<>("success", result), HttpStatus.OK);
    }
}
