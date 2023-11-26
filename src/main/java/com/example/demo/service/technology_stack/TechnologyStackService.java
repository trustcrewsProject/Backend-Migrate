package com.example.demo.service.technology_stack;

import com.example.demo.dto.common.ResponseDto;
import org.springframework.transaction.annotation.Transactional;

public interface TechnologyStackService {

    @Transactional(readOnly = true)
    ResponseDto<?> getTechnologyStackList();
}
