package com.example.demo.controller;

import com.example.demo.dto.board.*;
import com.example.demo.dto.common.ResponseDto;
import com.example.demo.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/board")
public class BoardController {

    @Autowired BoardService boardService;

    @PostMapping("")
    public ResponseEntity<ResponseDto<BoardProjectCreateResponseDto>> create(@RequestBody BoardProjectCreateRequestDto requestDto){
        try{
            BoardProjectCreateResponseDto result = boardService.create(requestDto);
            return ResponseEntity.ok(new ResponseDto("success", result));
        }catch(Exception exception){
            return ResponseEntity.badRequest().body(new ResponseDto("fail",exception.getMessage()));
        }
    }
}
