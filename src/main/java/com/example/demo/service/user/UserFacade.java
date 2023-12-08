package com.example.demo.service.user;

import com.example.demo.constant.Role;
import com.example.demo.constant.TrustScoreTypeIdentifier;
import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.trust_score.AddPointDto;
import com.example.demo.dto.trust_score.request.TrustScoreUpdateRequestDto;
import com.example.demo.dto.user.request.UserCreateRequestDto;
import com.example.demo.global.exception.customexception.TrustScoreCustomException;
import com.example.demo.model.position.Position;
import com.example.demo.model.technology_stack.TechnologyStack;
import com.example.demo.model.trust_score.TrustScore;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserTechnologyStack;
import com.example.demo.repository.trust_score.TrustScoreRepository;
import com.example.demo.service.position.PositionService;
import com.example.demo.service.technology_stack.TechnologyStackService;
import com.example.demo.service.trust_score.TrustScoreService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final PositionService positionService;
    private final TechnologyStackService technologyStackService;
    private final TrustScoreService trustScoreService;
    private final UserTechnologyStackService userTechnologyStackService;

    // 회원가입
    @Transactional
    public ResponseDto<?> createUser(UserCreateRequestDto createRequest) {
        // 패스워드 암호화
        String encryptPassword = passwordEncoder.encode(createRequest.getPassword());

        // 회원 포지션 조회
        Position position = positionService.findById(createRequest.getPositionId());

        // 회원 엔티티 생성
        User user =
                User.builder()
                        .email(createRequest.getEmail())
                        .password(encryptPassword)
                        .nickname(createRequest.getNickname())
                        .profileImgSrc("")
                        .intro(createRequest.getIntro())
                        .position(position)
                        .role(Role.USER)
                        .build();

        // 회원 저장
        User saveUser = userService.save(user);

        // 신뢰점수 저장
        TrustScore trustScore = addInitialTrustScoreReturnResponse(saveUser);

        // 회원에 신뢰점수 세팅
        saveUser.setTrustScore(trustScore);

        // 회원 기술스택 목록 저장
        List<UserTechnologyStack> userTechnologyStackList = saveTechStacksAndReturnResponse(saveUser, createRequest.getTechStackIds());

        // 회원 기술스택 목록 세팅
        saveUser.setTechStacks(userTechnologyStackList);

        return ResponseDto.success("회원등록이 완료되었습니다.", saveUser.getId());
    }

    // 신뢰점수 저장 및 반환
    private TrustScore addInitialTrustScoreReturnResponse(User user) {
        TrustScoreUpdateRequestDto trustScoreUpdateRequest = new TrustScoreUpdateRequestDto();
        trustScoreUpdateRequest.setUserId(user.getId());
        trustScoreUpdateRequest.setScoreTypeId(TrustScoreTypeIdentifier.NEW_MEMBER);

        // 회원의 신뢰점수 저장
        AddPointDto addPoint = new AddPointDto(trustScoreUpdateRequest);
        trustScoreService.addPoint(addPoint);

        // 해당 회원의 신뢰점수 반환
        return trustScoreService.findTrustScoreByUserId(user.getId());
    }

    // 회원 기술스택 목록 저장 및 반환
    private List<UserTechnologyStack> saveTechStacksAndReturnResponse(User user, List<Long> techStackIds) {
        // 요청한 기술스택 목록 조회
        List<TechnologyStack> techStackList = technologyStackService.findTechnologyStackListByIds(techStackIds);
        List<UserTechnologyStack> userTechStackList = createUserTechnologyStackList(user, techStackList);

        // 회원 기술스택 목록 저장
        return userTechnologyStackService.saveAll(userTechStackList);
    }

    // 회원 기술스택 목록 생성
    private List<UserTechnologyStack> createUserTechnologyStackList(User user, List<TechnologyStack> techStackList) {
        return techStackList.stream()
                .map(tech -> createUserTechnologyStack(user, tech))
                .collect(Collectors.toList());
    }

    // 회원 기술스택 엔티티 생성
    private UserTechnologyStack createUserTechnologyStack(User user, TechnologyStack technologyStack) {
        return UserTechnologyStack.builder()
                .user(user)
                .technologyStack(technologyStack)
                .build();
    }
}
