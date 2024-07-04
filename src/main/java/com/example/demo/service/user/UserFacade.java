package com.example.demo.service.user;

import com.example.demo.constant.OAuthProvider;
import com.example.demo.constant.Role;
import com.example.demo.constant.TrustScoreTypeIdentifier;
import com.example.demo.constant.UserStatus;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.oauth2.request.OAuth2UserCreateRequestDto;
import com.example.demo.dto.position.response.PositionInfoResponseDto;
import com.example.demo.dto.technology_stack.response.TechnologyStackInfoResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeInfoResponseDto;
import com.example.demo.dto.trust_score.AddPointDto;
import com.example.demo.dto.trust_score.request.TrustScoreUpdateRequestDto;
import com.example.demo.dto.user.request.UserCreateRequestDto;
import com.example.demo.dto.user.request.UserUpdateRequestDto;
import com.example.demo.dto.user.response.*;
import com.example.demo.global.exception.customexception.CommonCustomException;
import com.example.demo.global.exception.customexception.PageNationCustomException;
import com.example.demo.global.exception.customexception.UserCustomException;
import com.example.demo.global.log.PMLog;
import com.example.demo.model.position.Position;
import com.example.demo.model.technology_stack.TechnologyStack;
import com.example.demo.model.trust_grade.TrustGrade;
import com.example.demo.model.trust_score.TrustScore;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserTechnologyStack;
import com.example.demo.security.custom.PrincipalDetails;
import com.example.demo.service.file.AwsS3FileService;
import com.example.demo.service.position.PositionService;
import com.example.demo.service.technology_stack.TechnologyStackService;
import com.example.demo.service.trust_score.TrustScoreService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.example.demo.global.log.PMLog.USER_PROFILE;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final PositionService positionService;
    private final TechnologyStackService technologyStackService;
    private final TrustScoreService trustScoreService;
    private final UserProjectHistoryService userProjectHistoryService;
    private final AwsS3FileService awsS3FileService;

    /**
     * 회원가입 로직
     *
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
                        .status(UserStatus.ACTIVE)
                        .build();

        // 회원 저장
        User saveUser = userService.save(user);

        // 신뢰점수 저장
        TrustScore trustScore = addInitialTrustScoreReturnResponse(saveUser);

        // 회원에 신뢰점수 세팅
        user.setTrustScore(trustScore);

        // 회원 기술스택 목록 추가
        for(Long technologyStackId : createRequest.getTechStackIds()) {
            UserTechnologyStack userTechnologyStack = UserTechnologyStack.builder()
                    .user(saveUser)
                    .technologyStack(technologyStackService.findById(technologyStackId))
                    .build();
            saveUser.addTechStack(userTechnologyStack);
        }

        return ResponseDto.success("회원등록이 완료되었습니다.", saveUser.getId());
    }

    /**
     * 소셜 회원가입
     * @param oAuthUserCreateRequest
     * @return user.id
     */
    @Transactional
    public ResponseDto<?> createOAuthUser(OAuth2UserCreateRequestDto oAuthUserCreateRequest) {
        OAuthProvider oAuthProvider = OAuthProvider.findOAuthProvider(oAuthUserCreateRequest.getOAuthProvider());
        if(userService.existUserByOAuthProviderAndOAuthProviderId(oAuthProvider, oAuthUserCreateRequest.getOAuthProviderId())) {
            throw UserCustomException.ALREADY_OAUTH_USER;
        }

        // 회원 포지션 조회
        Position position = positionService.findById(oAuthUserCreateRequest.getPositionId());

        // 회원 엔티티 생성
        User user =
                User.builder()
                        .nickname(oAuthUserCreateRequest.getNickname())
                        .profileImgSrc("")
                        .intro(oAuthUserCreateRequest.getIntro())
                        .position(position)
                        .role(Role.USER)
                        .status(UserStatus.ACTIVE)
                        .oAuthProvider(oAuthProvider)
                        .oAuthProviderId(oAuthUserCreateRequest.getOAuthProviderId())
                        .build();

        // 회원 저장
        User oAuthUser = userService.save(user);

        // 신뢰점수 저장
        TrustScore trustScore = addInitialTrustScoreReturnResponse(oAuthUser);

        // 회원에 신뢰점수 세팅
        oAuthUser.setTrustScore(trustScore);

        // 회원 기술스택 목록 추가
        for(Long technologyStackId : oAuthUserCreateRequest.getTechStackIds()) {
            UserTechnologyStack userTechnologyStack = UserTechnologyStack.builder()
                    .user(oAuthUser)
                    .technologyStack(technologyStackService.findById(technologyStackId))
                    .build();
            oAuthUser.addTechStack(userTechnologyStack);
        }

        return ResponseDto.success("OAuth 회원등록이 완료되었습니다.", oAuthUser.getId());
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
     *
     * @param user
     * @param updateRequest
     * @return updateResponse
     */
    @Transactional
    public ResponseDto<?> updateUser(PrincipalDetails user, MultipartFile file, UserUpdateRequestDto updateRequest) {
        User currentUser = userService.getUserForUpdate(user.getId());

        // 이미지 파일이 존재할 경우, 이미지 변경 수행
        if(Objects.nonNull(file) && !file.isEmpty()) {
            String profileImgSrc = currentUser.getProfileImgSrc();

            // 기존 회원의 프로필 이미지가 존재할 경우 삭제
            if(Objects.nonNull(profileImgSrc) && !profileImgSrc.isEmpty()) {
                awsS3FileService.deleteImage(profileImgSrc);
            }

            try {
                currentUser.updateProfileImgSrc(awsS3FileService.uploadImage(file));
            } catch (IOException e) {
                PMLog.e(USER_PROFILE, e.getStackTrace(), e);
                throw CommonCustomException.INTERNAL_SERVER_ERROR;
            }
        }

        // 기존 포지션과 수정 요청한 포지션 비교
        Position position = currentUser.getPosition();
        // 다르면 포지션 수정
        if (!isPositionDiff(position.getId(), updateRequest.getPositionId())) {
            position = positionService.findById(updateRequest.getPositionId());
        }

        // 삭제할 기술스택 확인
        List<UserTechnologyStack> deleteList =
                hasTechStackToRemove(currentUser.getTechStacks(), updateRequest.getTechStackIds());
        if (!deleteList.isEmpty()) {
            // 회원 기술스택 목록에서 삭제
            deleteList.forEach(currentUser::removeTechStack);
        }

        // 추가할 기술스택 확인
        List<TechnologyStack> addTechStacks =
                hasTechStackToAdd(currentUser.getTechStacks(), updateRequest.getTechStackIds());
        if (Objects.nonNull(addTechStacks)) {
            List<UserTechnologyStack> addList = addTechStacks.stream()
                    .map(technologyStack -> UserTechnologyStack.builder()
                            .user(currentUser)
                            .technologyStack(technologyStack)
                            .build())
                    .collect(Collectors.toList());

            // 회원 기술스택 목록에 추가
            addList.forEach(currentUser::addTechStack);
        }

        // 회원 수정
        currentUser.update(updateRequest.getNickname(), position, updateRequest.getIntro());

        // 수정된 회원의 기술스택 목록 List<TechnologyStackInfoResponseDto> 타입으로 변환
        List<TechnologyStackInfoResponseDto> infoTechStacks =
                currentUser.getTechStacks().stream()
                        .map(
                                userTechnologyStack ->
                                        TechnologyStackInfoResponseDto.of(
                                                userTechnologyStack.getTechnologyStack().getId(),
                                                userTechnologyStack.getTechnologyStack().getName()))
                        .collect(Collectors.toList());

        // 회원 수정 응답 객체 생성
        UserUpdateResponseDto updateResponse =
                UserUpdateResponseDto.of(
                        currentUser.getEmail(),
                        currentUser.getNickname(),
                        PositionInfoResponseDto.of(position.getId(), position.getName()),
                        infoTechStacks,
                        currentUser.getIntro());

        return ResponseDto.success("회원수정이 완료되었습니다.", updateResponse);
    }

    // 기존 포지션과 요청한 포지션 비교
    private boolean isPositionDiff(Long currentPositionId, Long requestUpdatePositionId) {
        return currentPositionId.equals(requestUpdatePositionId);
    }

    // 기존 기술스택 목록 중 삭제해야 될 기술스택 조회
    private List<UserTechnologyStack> hasTechStackToRemove(
            List<UserTechnologyStack> currentTechStackList, List<Long> requestUpdateTechStackList) {
        List<UserTechnologyStack> deleteList = new ArrayList<>();
        for (UserTechnologyStack userTechnologyStack : currentTechStackList) {
            TechnologyStack technologyStack = userTechnologyStack.getTechnologyStack();

            if (!requestUpdateTechStackList.contains(technologyStack.getId())) {
                deleteList.add(userTechnologyStack);
            }
        }

        return deleteList;
    }

    // 요청한 기술스택 목록 중 추가해야 될 기술스택 조회
    private List<TechnologyStack> hasTechStackToAdd(
            List<UserTechnologyStack> currentTechStackList, List<Long> requestUpdateTechStackList) {
        List<Long> currentList =
                currentTechStackList.stream()
                        .map(
                                userTechnologyStack ->
                                        userTechnologyStack.getTechnologyStack().getId())
                        .collect(Collectors.toList());

        requestUpdateTechStackList.removeAll(currentList);

        if (!requestUpdateTechStackList.isEmpty()) {
            return technologyStackService.findTechnologyStackListByIds(requestUpdateTechStackList);
        }

        return null;
    }

    /**
     * 간단한 내 정보 조회 로직 (닉네임, 프로필 이미지 경로) - 로그인한 회원의 정보를 클라이언트 헤더에서 사용하기 위함
     *
     * @param user
     * @return simpleMyInfoResponse
     */
    @Transactional(readOnly = true)
    public ResponseDto<?> getSimpleMyInfo(PrincipalDetails user) {
        User currentUser = userService.findById(user.getId());
        UserSimpleInfoResponseDto simpleMyInfoResponse =
                UserSimpleInfoResponseDto.of(
                        currentUser.getNickname(), currentUser.getProfileImgSrc());

        return ResponseDto.success("내 정보 조회가 완료되었습니다.", simpleMyInfoResponse);
    }

    /**
     * 내 정보 조회 로직
     *
     * @param user
     * @return UserMyInfoResponseDto myInfoResponse
     */
    @Transactional(readOnly = true)
    public ResponseDto<?> getMyInfo(PrincipalDetails user) {
        User currentUser = userService.fetchUserDetails(user.getId());

        // 신뢰점수
        TrustScore trustScore = currentUser.getTrustScore();
        // 신뢰등급
        TrustGrade trustGrade = trustScore.getTrustGrade();
        // 포지션
        Position position = currentUser.getPosition();
        // 기술스택목록
        List<TechnologyStack> techStacks =
                currentUser.getTechStacks().stream()
                        .map(UserTechnologyStack::getTechnologyStack)
                        .collect(Collectors.toList());

        // 신뢰등급 정보 응답 DTO
        TrustGradeInfoResponseDto trustGradeInfo =
                TrustGradeInfoResponseDto.of(trustGrade.getId(), trustGrade.getName());
        // 포지션 정보 응답 DTO
        PositionInfoResponseDto positionInfo =
                PositionInfoResponseDto.of(position.getId(), position.getName());
        // 기술스택목록 정보 응답 DTO
        List<TechnologyStackInfoResponseDto> techStacksInfo =
                techStacks.stream()
                        .map(
                                technologyStack ->
                                        TechnologyStackInfoResponseDto.of(
                                                technologyStack.getId(), technologyStack.getName()))
                        .collect(Collectors.toList());
        // 회원 프로젝트 이력 개수
        long projectHistoryTotalCount =
                userProjectHistoryService.getUserProjectHistoryTotalCount(currentUser.getId(), null);

        // 내 정보 응답 DTO 생성
        UserMyInfoResponseDto myInfoResponse =
                UserMyInfoResponseDto.of(
                        currentUser.getId(),
                        currentUser.getEmail(),
                        currentUser.getNickname(),
                        currentUser.getProfileImgSrc(),
                        currentUser.getIntro(),
                        trustScore.getScore(),
                        trustGradeInfo,
                        positionInfo,
                        techStacksInfo,
                        projectHistoryTotalCount,
                        currentUser.getCreateDate(),
                        currentUser.getUpdateDate());

        return ResponseDto.success("내 정보 조회가 완료되었습니다.", myInfoResponse);
    }

    /**
     * 사용자 정보 조회 로직
     *
     * @param userId
     * @return UserMyInfoResponseDto myInfoResponse
     */
    @Transactional(readOnly = true)
    public ResponseDto<?> getUserInfoById(long userId) {
        User currentUser = userService.fetchUserDetails(userId);

        // 신뢰점수
        TrustScore trustScore = currentUser.getTrustScore();
        // 신뢰등급
        TrustGrade trustGrade = trustScore.getTrustGrade();
        // 포지션
        Position position = currentUser.getPosition();
        // 기술스택목록
        List<TechnologyStack> techStacks =
                currentUser.getTechStacks().stream()
                        .map(UserTechnologyStack::getTechnologyStack)
                        .collect(Collectors.toList());

        // 신뢰등급 정보 응답 DTO
        TrustGradeInfoResponseDto trustGradeInfo =
                TrustGradeInfoResponseDto.of(trustGrade.getId(), trustGrade.getName());
        // 포지션 정보 응답 DTO
        PositionInfoResponseDto positionInfo =
                PositionInfoResponseDto.of(position.getId(), position.getName());
        // 기술스택목록 정보 응답 DTO
        List<TechnologyStackInfoResponseDto> techStacksInfo =
                techStacks.stream()
                        .map(
                                technologyStack ->
                                        TechnologyStackInfoResponseDto.of(
                                                technologyStack.getId(), technologyStack.getName()))
                        .collect(Collectors.toList());
        // 회원 프로젝트 이력 개수
        long projectHistoryTotalCount =
                userProjectHistoryService.getUserProjectHistoryTotalCount(currentUser.getId(), null);

        // 내 정보 응답 DTO 생성
        UserMyInfoResponseDto myInfoResponse =
                UserMyInfoResponseDto.of(
                        currentUser.getId(),
                        currentUser.getEmail(),
                        currentUser.getNickname(),
                        currentUser.getProfileImgSrc(),
                        currentUser.getIntro(),
                        trustScore.getScore(),
                        trustGradeInfo,
                        positionInfo,
                        techStacksInfo,
                        projectHistoryTotalCount,
                        currentUser.getCreateDate(),
                        currentUser.getUpdateDate());

        return ResponseDto.success("사용자 정보 조회가 완료되었습니다.", myInfoResponse);
    }

    /**
     * 내 프로젝트 이력 목록 조회 (페이징)
     *
     * @param user
     * @param pageNumber
     * @return PaginationResponseDto
     */
    @Transactional(readOnly = true)
    public ResponseDto<?> getMyProjectHistoryList(PrincipalDetails user, int pageNumber) {
        // 페이지 번호가 0번보다 작은 경우
        if (pageNumber < 0) {
            throw PageNationCustomException.INVALID_PAGE_NUMBER;
        }

        PaginationResponseDto projectHistoryList =
                userProjectHistoryService.getUserProjectHistoryList(user.getId(), pageNumber);

        return ResponseDto.success("내 프로젝트 이력 목록 조회가 완료되었습니다.", projectHistoryList);
    }

    /**
     * 내 프로필 이미지 삭제 (aws s3에 저장된 이미지 파일 삭제)
     * @param userId
     * @return
     */
    @Transactional
    public ResponseDto<?> deleteMyProfileImg(Long userId) {
        User currentUser = userService.findById(userId);

        // 요청한 회원의 프로필 이미지가 존재하지 않는 경우 예외처리
        if(Objects.isNull(currentUser.getProfileImgSrc())
                || currentUser.getProfileImgSrc().equals("")) {
            throw UserCustomException.DOES_NOT_EXIST_PROFILE_IMG;
        }

        // aws s3 저장된 파일 삭제
        awsS3FileService.deleteImage(currentUser.getProfileImgSrc());

        // 회원의 프로필 이미지 경로를 빈 값으로 수정
        currentUser.updateProfileImgSrc("");

        return ResponseDto.success("프로필 이미지가 삭제되었습니다.");
    }

    /**
     * 회원 탈퇴
     * @param userId
     * @return
     */
    @Transactional
    public ResponseDto<?> deleteUser(Long userId) {
        User currentUser = userService.findById(userId);

        // 회원 삭제(회원 엔티티의 status 필드에 UserStatus.DELETED 적용)
        currentUser.deleteUser();

        // 회원 기술스택 목록 삭제
        currentUser.removeAllTechStacks();

        return ResponseDto.success("회원 탈퇴가 완료되었습니다.");
    }
}
