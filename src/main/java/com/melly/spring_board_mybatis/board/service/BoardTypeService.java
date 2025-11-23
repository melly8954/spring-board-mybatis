package com.melly.spring_board_mybatis.board.service;

import com.melly.spring_board_mybatis.board.dto.BoardTypeResponse;

import java.util.List;

public interface BoardTypeService {
    List<BoardTypeResponse> getBoardTypes();
}
