package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  ResponseDto
 *  Controller, Service return 값으로 사용
 *  D에는 Entity, Dto, List<Entity>, List<Dto> 등이 들어올 수 있음.
 */

@Data
@AllArgsConstructor(staticName = "set")
public class ResponseDto<D> {
    private boolean result;
    private String message;
    private D data;

    public static <D> ResponseDto<D> setSuccess(String message, D data){
        return ResponseDto.set(true, message, data);
    }

    //fail인 경우
    public static <D> ResponseDto<D> setFailed(String message){
        return ResponseDto.set(false, message, null);
    }
}
