package com.example.demo.controller.project.setting.crewAuth;


import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.project.setting.request.ProjectSettingCrewAuthUpdReqDto;
import com.example.demo.security.custom.PrincipalDetails;
import com.example.demo.service.project.ProjectMemberFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project/setting/crewAuth")
@RequiredArgsConstructor
public class ProjectSettingCrewAuthController {

    public final ProjectMemberFacade projectMemberFacade;

    @PutMapping("")
    public ResponseEntity<ResponseDto<?>> updateProjectSettingCrewAuth(
            @AuthenticationPrincipal PrincipalDetails user,
            @RequestBody ProjectSettingCrewAuthUpdReqDto requestDto
    ) {
        projectMemberFacade.updateProjectMemberAuth(user.getId(), requestDto);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

}
