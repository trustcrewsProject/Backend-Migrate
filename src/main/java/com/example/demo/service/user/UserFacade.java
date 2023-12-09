package com.example.demo.service.user;

import com.example.demo.constant.Role;
import com.example.demo.constant.TrustScoreTypeIdentifier;
import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.position.response.PositionInfoResponseDto;
import com.example.demo.dto.technology_stack.response.TechnologyStackInfoResponseDto;
import com.example.demo.dto.trust_score.AddPointDto;
import com.example.demo.dto.trust_score.request.TrustScoreUpdateRequestDto;
import com.example.demo.dto.user.request.UserCreateRequestDto;
import com.example.demo.dto.user.request.UserUpdateRequestDto;
import com.example.demo.dto.user.response.UserUpdateResponseDto;
import com.example.demo.global.exception.customexception.TrustScoreCustomException;
import com.example.demo.model.position.Position;
import com.example.demo.model.technology_stack.TechnologyStack;
import com.example.demo.model.trust_score.TrustScore;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserTechnologyStack;
import com.example.demo.repository.trust_score.TrustScoreRepository;
import com.example.demo.security.custom.PrincipalDetails;
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

    /**
     * 회원가입 로직
     * @param createRequest
     * @return user.id
     */
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

    /**
     * 회원수정 로직
     * @param user
     * @param updateRequest
     * @return updateResponse
     */
    @Transactional
    public ResponseDto<?> updateUser(PrincipalDetails user, UserUpdateRequestDto updateRequest) {
        User currentUser = userService.getUserForUpdate(user.getId());

        // 기존 포지션과 수정 요청한 포지션 비교
        Position position = currentUser.getPosition();
        // 다르면 포지션 수정
        if(!isPositionDiff(position.getId(), updateRequest.getPositionId())) {
            position = positionService.findById(updateRequest.getPositionId());
        }

        // 삭제할 기술스택 확인
        List<UserTechnologyStack> deleteList = hasTechStackToRemove(currentUser.getTechStacks(), updateRequest.getTechStackIds());
        if(!deleteList.isEmpty()) {
            // 저장된 UserTechnologyStack 삭제
            userTechnologyStackService.deleteUserTechnologyStackList(deleteList);
            // 회원 기술스택 목록에서 삭제
            deleteList.forEach(currentUser::removeTechStack);
        }

        // 추가할 기술스택 확인
        List<Long> addTechStackIds = hasTechStackToAdd(currentUser.getTechStacks(), updateRequest.getTechStackIds());
        if(!addTechStackIds.isEmpty()) {
            // 새로운 UserTechnologyStack 저장
            List<UserTechnologyStack> addList = saveTechStacksAndReturnResponse(currentUser, addTechStackIds);
            // 회원 기술스택 목록에 추가
            addList.forEach(currentUser::addTechStack);
        }

        // 회원 수정
        currentUser.update(updateRequest.getNickname(), position, updateRequest.getIntro());

        // 수정된 회원의 기술스택 목록 List<TechnologyStackInfoResponseDto> 타입으로 변환
        List<TechnologyStackInfoResponseDto> infoTechStacks = currentUser.getTechStacks().stream()
                .map(userTechnologyStack -> TechnologyStackInfoResponseDto.of(userTechnologyStack.getTechnologyStack().getId(), userTechnologyStack.getTechnologyStack().getName()))
                .collect(Collectors.toList());

        // 회원 수정 응답 객체 생성
        UserUpdateResponseDto updateResponse = UserUpdateResponseDto.of(currentUser.getEmail(), currentUser.getNickname(),
                PositionInfoResponseDto.of(position.getId(), position.getName()),
                infoTechStacks, currentUser.getIntro());

        return ResponseDto.success("회원수정이 완료되었습니다.", updateResponse);
    }

    // 기존 포지션과 요청한 포지션 비교
    private boolean isPositionDiff(Long currentPositionId, Long requestUpdatePositionId) {
        return currentPositionId.equals(requestUpdatePositionId);
    }

    // 기존 기술스택 목록 중 삭제해야 될 기술스택이 있는지 조회
    private List<UserTechnologyStack> hasTechStackToRemove(List<UserTechnologyStack> currentTechStackList, List<Long> requestUpdateTechStackList) {
        List<UserTechnologyStack> deleteList = new ArrayList<>();
        for(UserTechnologyStack userTechnologyStack : currentTechStackList) {
            TechnologyStack technologyStack = userTechnologyStack.getTechnologyStack();

            if(!requestUpdateTechStackList.contains(technologyStack.getId())) {
                deleteList.add(userTechnologyStack);
            }
        }

        return deleteList;
    }

    // 요청한 기술스택 목록 중 추가해야 될 기술스택이 있는지 조회
    private List<Long> hasTechStackToAdd(List<UserTechnologyStack> currentTechStackList, List<Long> requestUpdateTechStackList) {
        List<Long> currentList = currentTechStackList.stream()
                .map(userTechnologyStack -> userTechnologyStack.getTechnologyStack().getId())
                .collect(Collectors.toList());

        requestUpdateTechStackList.removeAll(currentList);

        return requestUpdateTechStackList;
    }
}
