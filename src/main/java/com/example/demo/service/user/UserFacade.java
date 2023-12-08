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

        // 회원 포지션
        Position position = positionService.findById(createRequest.getPositionId());

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

        // 신뢰점수 테이블에 회원 신뢰점수 저장
        TrustScoreUpdateRequestDto trustScoreUpdateRequest = new TrustScoreUpdateRequestDto();
        trustScoreUpdateRequest.setUserId(saveUser.getId());
        trustScoreUpdateRequest.setScoreTypeId(TrustScoreTypeIdentifier.NEW_MEMBER);

        AddPointDto addPoint = new AddPointDto(trustScoreUpdateRequest);
        trustScoreService.addPoint(addPoint);

        // 회원에 신뢰점수 세팅
        saveUser.setTrustScore(trustScoreService.findTrustScoreByUserId(user.getId()));

        // 요청한 기술스택 목록 조회
        List<TechnologyStack> techStackList = technologyStackService.findTechnologyStackListByIds(createRequest.getTechStackIds());

        // 회원 기술스택 목록 저장
        List<UserTechnologyStack> userTechStackList = createAndSaveUserTechnologyStacks(saveUser, techStackList);

        // 회원 기술스택 목록 세팅
        saveUser.setTechStacks(userTechStackList);

        return ResponseDto.success("회원등록이 완료되었습니다.", saveUser.getId());
    }

    // UserTechnologyStack 생성 및 저장
    private List<UserTechnologyStack> createAndSaveUserTechnologyStacks(User user, List<TechnologyStack> techStackList) {
        List<UserTechnologyStack> userTechnologyStackList = new ArrayList<>();

        // 회원 엔티티와 기술스택 목록으로 UserTechnologyStack 엔티티 생성
        for(TechnologyStack technologyStack : techStackList) {
            UserTechnologyStack userTechnologyStack = UserTechnologyStack.builder()
                    .user(user)
                    .technologyStack(technologyStack)
                    .build();

            userTechnologyStackList.add(userTechnologyStack);
        }

        // UserTechnologyStack 저장하고 리스트로 반환
        return userTechnologyStackService.saveAll(userTechnologyStackList);
    }
}
