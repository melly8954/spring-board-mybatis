package com.melly.spring_board_mybatis.board.mapper;

import com.melly.spring_board_mybatis.board.domain.BoardType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardTypeMapper {
    List<BoardType> findAll();
    BoardType findById(Long boardTypeId);
}
