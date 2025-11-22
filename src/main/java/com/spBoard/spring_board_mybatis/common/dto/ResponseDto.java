package com.spBoard.spring_board_mybatis.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ResponseDto<T> {
    private String id;
    private int code;
    private String errorCode;
    private String message;
    private T data;
}
