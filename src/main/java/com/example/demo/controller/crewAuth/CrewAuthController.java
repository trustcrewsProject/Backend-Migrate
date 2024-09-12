package com.example.demo.controller.crewAuth;


import com.example.demo.constant.ProjectMemberAuth;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.projectmember.ProjectMemberAuthDto;
import com.example.demo.global.log.PMLog;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/crewAuth/public")
@RequiredArgsConstructor
public class CrewAuthController {

    /**
     * 프로젝트 크루 권한 목록 조회
     *
     * @return
     */
    @GetMapping("")
    public ResponseEntity<ResponseDto<?>> getCrewAuthOptions() {

        List<ProjectMemberAuthDto<ProjectMemberAuth>> authDtos =
                Arrays.stream(ProjectMemberAuth.values())
                                .map(ProjectMemberAuthDto::of)
                                .collect(Collectors.toList());

        PaginationResponseDto result = PaginationResponseDto.of(authDtos, authDtos.size());

        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }
}