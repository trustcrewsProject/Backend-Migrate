package com.example.demo.controller.project;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.project.request.ProjectInfoUpdateRequestDto;
import com.example.demo.dto.project.setting.request.ProjectSettingBoardUpdRequestDto;
import com.example.demo.dto.project.setting.request.ProjectSettingInfoUpdRequestDto;
import com.example.demo.security.custom.PrincipalDetails;
import com.example.demo.service.project.ProjectFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project/setting")
@RequiredArgsConstructor
public class ProjectSettingController {

    public final ProjectFacade projectFacade;

    /**
     * 프로젝트 세팅 - 게시글 정보 조회
     *
     * @param user
     * @param projectId
     * @return
     */
    @GetMapping("/board/{projectId}")
    public ResponseEntity<ResponseDto<?>> getProjectSettingBoard(
            @AuthenticationPrincipal PrincipalDetails user,
            @PathVariable("projectId") Long projectId
    ) {
        return new ResponseEntity<>(ResponseDto.success("success",
                projectFacade.getProjectSettingBoardInfo(user.getId(), projectId)), HttpStatus.OK);
    }

    /**
     * 프로젝트 세팅 - 게시글 정보 수정
     *
     * @param user
     * @param requestDto
     * @return
     */
    @PutMapping("/board")
    public ResponseEntity<ResponseDto<?>> updateProjectSettingBoard(
            @AuthenticationPrincipal PrincipalDetails user,
            @RequestBody ProjectSettingBoardUpdRequestDto requestDto
    ) {
        projectFacade.updateProjectSettingBoard(user.getId(), requestDto);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }


    /**
     * 프로젝트 세팅 - 프로젝트 정보 조회
     *
     * @param user
     * @param projectId
     * @return
     */
    @GetMapping("/info/{projectId}")
    public ResponseEntity<ResponseDto<?>> getProjectSettinginfo(
            @AuthenticationPrincipal PrincipalDetails user,
            @PathVariable("projectId") Long projectId
    ) {
        return new ResponseEntity<>(ResponseDto.success("success",
                projectFacade.getProjectSettingInfo(user.getId(), projectId)), HttpStatus.OK);
    }

    /**
     * 프로젝트 세팅 - 프로젝트 정보 수정
     * @param user
     * @param requestDto
     * @return
     */
    @PutMapping("/info")
    public ResponseEntity<ResponseDto<?>> updateProjectSettingInfo(
            @AuthenticationPrincipal PrincipalDetails user,
            @RequestBody ProjectSettingInfoUpdRequestDto requestDto
    ) {
        projectFacade.updateProjectSettingInfo(user.getId(), requestDto);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    // todo - 삭제
//    @PutMapping("")
//    public ResponseEntity<ResponseDto<?>> updateProject(
//            @AuthenticationPrincipal PrincipalDetails user,
//            @RequestBody ProjectInfoUpdateRequestDto updateRequest) {
//        projectFacade.updateProject(user.getId(), updateRequest);
//        return new ResponseEntity<>(ResponseDto.success("프로젝트 정보 수정이 완료되었습니다."), HttpStatus.OK);
//    }


}
