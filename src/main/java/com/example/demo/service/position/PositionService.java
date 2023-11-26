package com.example.demo.service.position;

import com.example.demo.dto.common.ResponseDto;
import org.springframework.transaction.annotation.Transactional;

public interface PositionService {

    // 포지션 목록 가져오기
    @Transactional(readOnly = true)
    ResponseDto<?> getPositionList();
}
