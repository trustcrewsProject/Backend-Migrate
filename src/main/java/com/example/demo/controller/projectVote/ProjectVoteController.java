package com.example.demo.controller.projectVote;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.projectVote.fwithdraw.VoteFWithdrawRequestDto;
import com.example.demo.dto.projectVote.recruit.ProjectVoteRecruitRequestDto;
import com.example.demo.security.custom.PrincipalDetails;
import com.example.demo.service.projectVote.fwithdraw.VFWithdrawFacade;
import com.example.demo.service.projectVote.recruit.VoteRecruitFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projectVote")
@RequiredArgsConstructor
public class ProjectVoteController {

    public final VoteRecruitFacade voteRecruitFacade;
    public final VFWithdrawFacade vfWithdrawFacade;

    // 프로젝트 모집 투표
    @PostMapping("/recruit")
    public ResponseEntity<ResponseDto<?>> voteForRecruit(
            @AuthenticationPrincipal PrincipalDetails user,
            @RequestBody ProjectVoteRecruitRequestDto requestDto
    ){
        voteRecruitFacade.voteForProjectRecruit(user.getId(), requestDto);
        return new ResponseEntity<>(
                ResponseDto.success("success", null), HttpStatus.OK
        );
    }

    @PostMapping("/fwithdraw")
    public ResponseEntity<ResponseDto<?>> voteForFWithdraw(
            @AuthenticationPrincipal PrincipalDetails user,
            @RequestBody VoteFWithdrawRequestDto requestDto
    ){
        vfWithdrawFacade.voteForFWithdraw(user.getId(), requestDto);
        return new ResponseEntity<>(
                ResponseDto.success("success", null), HttpStatus.OK
        );
    }


}
