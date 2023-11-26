package com.example.demo.service.technology_stack;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.technology_stack.response.TechnologyStackInfoResponseDto;
import com.example.demo.repository.technology_stack.TechnologyStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TechnologyStackServiceImpl implements TechnologyStackService {

    private final TechnologyStackRepository technologyStackRepository;

    @Override
    public ResponseDto<?> getTechnologyStackList() {
        List<TechnologyStackInfoResponseDto> techStackList = technologyStackRepository.findAll().stream()
                .map(technologyStack -> TechnologyStackInfoResponseDto.of(technologyStack.getId(), technologyStack.getName()))
                .collect(Collectors.toList());

        return ResponseDto.success("기술스택 목록 조회가 완료되었습니다.", techStackList);
    }
}
