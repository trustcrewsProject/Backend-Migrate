package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.TrustGradeRequestDto;
import com.example.demo.dto.TrustGradeResponseDto;
import com.example.demo.service.TrustGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("api/trustgrade")
public class TrustGradeController {

    @Autowired
    private TrustGradeService trustGradeService;

    @PostMapping("")
    public ResponseDto<?> create(@Valid @RequestBody TrustGradeRequestDto requestDto){
        return trustGradeService.create(requestDto);
    }

    @PatchMapping("")
    public ResponseDto<?> update(@RequestBody TrustGradeRequestDto requestDto){
        return trustGradeService.update(requestDto);
    }

    @DeleteMapping("/{trustGradeId}")
    public ResponseDto<?> delete(@PathVariable("trustGradeId") Long trustGradeId){
        return trustGradeService.delete(trustGradeId);
    }
}
