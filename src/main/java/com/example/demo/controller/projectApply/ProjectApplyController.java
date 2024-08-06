package com.example.demo.controller.projectApply;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.projectApply.ProjectApplyRequestDto;
import com.example.demo.security.custom.PrincipalDetails;
import com.example.demo.service.projectApply.ProjectApplyFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/projectApply")
@RequiredArgsConstructor
public class ProjectApplyController {

    public final ProjectApplyFacade projectApplyFacade;

    @PostMapping("")
    public ResponseEntity<ResponseDto<?>> projectApply(
            @AuthenticationPrincipal PrincipalDetails user,
            @RequestBody ProjectApplyRequestDto projectApplyRequestDto) {
        projectApplyFacade.projectApply(user.getId(), projectApplyRequestDto);
        return new ResponseEntity<>(ResponseDto.success("success", null), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<ResponseDto<?>> getMyProjectApplies(
            @AuthenticationPrincipal PrincipalDetails user,
            @RequestParam("pageIndex") Optional<Integer> pageIndex,
            @RequestParam("itemCount") Optional<Integer> itemCount) {
        return new ResponseEntity<>(ResponseDto.success("success",
                projectApplyFacade.getMyApplyProjects
                        (
                                user.getId(),
                                pageIndex.orElse(0),
                                itemCount.orElse(5)
                        )),
                HttpStatus.OK
        );
    }

}
