package com.melly.spring_board_mybatis.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardType {
    private Long boardTypeId;
    private String code;
    private String name;
    private String description;
}
