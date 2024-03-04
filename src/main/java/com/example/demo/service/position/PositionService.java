package com.example.demo.service.position;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.model.position.Position;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PositionService {

    // 포지션 목록 가져오기
    @Transactional(readOnly = true)
    ResponseDto<?> getPositionList();

    Position save(Position position);

    Position findById(Long id);
}
