package com.melly.spring_board_mybatis.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class BoardTypeResponse {
    private Long boardTypeId;
    private String boardTypeCode;
    private String boardTypeName;
}
