package com.example.demo.service.user;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.user.request.UserCreateRequestDto;
import com.example.demo.service.position.PositionService;
import com.example.demo.service.technology_stack.TechnologyStackService;
import com.example.demo.service.trust_score.TrustScoreService;
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
}
