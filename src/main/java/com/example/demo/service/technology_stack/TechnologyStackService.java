package com.example.demo.service.technology_stack;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.model.technology_stack.TechnologyStack;
import org.springframework.transaction.annotation.Transactional;

public interface TechnologyStackService {

    @Transactional(readOnly = true)
    ResponseDto<?> getTechnologyStackList();

    @Transactional(readOnly = true)
    TechnologyStack findById(Long id);
}
