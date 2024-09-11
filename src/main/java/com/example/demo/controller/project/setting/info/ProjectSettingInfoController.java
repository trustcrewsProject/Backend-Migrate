package com.example.demo.controller.project.setting.info;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.project.setting.request.ProjectSettingInfoUpdRequestDto;
import com.example.demo.security.custom.PrincipalDetails;
import com.example.demo.service.project.ProjectFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project/setting/info")
@RequiredArgsConstructor
public class ProjectSettingInfoController {

    public final ProjectFacade projectFacade;

    /**
     * 프로젝트 세팅 - 프로젝트 정보 조회
     *
     * @param projectId
     * @return
     */
    @GetMapping("/{projectId}")
    public ResponseEntity<ResponseDto<?>> getProjectSettinginfo(
            @PathVariable("projectId") Long projectId
    ) {
        return new ResponseEntity<>(ResponseDto.success("success",
                projectFacade.getProjectSettingInfo(projectId)), HttpStatus.OK);
    }

    /**
     * 프로젝트 세팅 - 프로젝트 정보 수정
     * @param user
     * @param requestDto
     * @return
     */
    @PutMapping("")
    public ResponseEntity<ResponseDto<?>> updateProjectSettingInfo(
            @AuthenticationPrincipal PrincipalDetails user,
            @RequestBody ProjectSettingInfoUpdRequestDto requestDto
    ) {
        projectFacade.updateProjectSettingInfo(user.getId(), requestDto);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }


}
