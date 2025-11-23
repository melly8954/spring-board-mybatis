package com.melly.spring_board_mybatis.board.dto;

import com.melly.spring_board_mybatis.common.dto.SearchParamDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BoardFilter extends SearchParamDto {
    private String boardTypeCode;
    private String searchType;
    private String searchKeyword;
}
