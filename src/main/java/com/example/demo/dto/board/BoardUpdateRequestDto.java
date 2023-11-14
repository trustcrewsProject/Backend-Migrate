package com.example.demo.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.List;


@Getter
@AllArgsConstructor
public class BoardUpdateRequestDto {
    @NotBlank(message = "게시물 아이디는 필수 입력 값입니다.")
    private long boardId;
    @NotBlank(message = "게시물 제목은 필수 입력 값입니다.")
    private String boardTitle;
    @NotBlank(message = "게시물 연락은 필수 입력 값입니다.")
    private String boardContact;
    @NotBlank(message = "게시물 제목은 필수 입력 값입니다.")
    private String boardContent;

    @NotBlank(message = "게시물 모집 분야는 필수 입력 값입니다.")
    private List<Position> boardPositions;
}
