package com.example.demo.controller.crewAuth;


import com.example.demo.constant.ProjectMemberAuth;
import com.example.demo.dto.common.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CrewAuthController {

    /**
     * 프로젝트 크루 권한 목록 조회
     * @return
     */
    @GetMapping("/api/crewAuth/public")
    public ResponseEntity<ResponseDto<?>> getCrewAuthOptions() {
        return new ResponseEntity<>(ResponseDto.success("success", ProjectMemberAuth.values()), HttpStatus.OK);
    }
}
