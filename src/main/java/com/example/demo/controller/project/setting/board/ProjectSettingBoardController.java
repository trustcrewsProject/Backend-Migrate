package com.example.demo.controller.project.setting.board;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.project.setting.request.ProjectSettingBoardUpdRequestDto;
import com.example.demo.security.custom.PrincipalDetails;
import com.example.demo.service.project.ProjectFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project/setting/board")
@RequiredArgsConstructor
public class ProjectSettingBoardController {

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
    @PutMapping("")
    public ResponseEntity<ResponseDto<?>> updateProjectSettingBoard(
            @AuthenticationPrincipal PrincipalDetails user,
            @RequestBody ProjectSettingBoardUpdRequestDto requestDto
    ) {
        projectFacade.updateProjectSettingBoard(user.getId(), requestDto);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }


}
