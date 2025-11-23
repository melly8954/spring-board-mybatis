package com.melly.spring_board_mybatis.board.mapper;

import com.melly.spring_board_mybatis.board.domain.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {
    void insertBoard(Board board);

    List<Board> searchBoard(
            @Param("boardTypeCode") String boardTypeCode,
            @Param("searchType") String searchType,
            @Param("searchKeyword") String searchKeyword,
            @Param("offset") int offset,
            @Param("limit") int limit,
            @Param("orderBy") String orderBy
    );

    int countBoard(
            @Param("boardTypeCode") String boardTypeCode,
            @Param("searchType") String searchType,
            @Param("searchKeyword") String searchKeyword
    );
}
