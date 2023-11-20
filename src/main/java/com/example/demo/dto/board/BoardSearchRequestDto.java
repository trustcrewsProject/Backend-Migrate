package com.example.demo.dto.board;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BoardSearchRequestDto {
    private List<Long> technologyIds;
    private String keyWord;
    private List<Long> positionIds;
}
