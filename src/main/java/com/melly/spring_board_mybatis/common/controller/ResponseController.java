package com.melly.spring_board_mybatis.common.controller;

import com.melly.spring_board_mybatis.common.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ResponseController {
    default <T> ResponseEntity<ResponseDto<T>> makeResponseEntity(String traceId, HttpStatus status, String errorCode, String message, T data) {
        ResponseDto<T> dto = ResponseDto.<T>builder()
                .id(traceId)
                .code(status.value())
                .errorCode(errorCode)
                .message(message)
                .data(data)
                .build();
        return ResponseEntity.status(status).body(dto);
    }
}
