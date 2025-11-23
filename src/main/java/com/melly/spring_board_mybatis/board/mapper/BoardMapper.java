package com.melly.spring_board_mybatis.board.mapper;

import com.melly.spring_board_mybatis.board.domain.Board;
import com.melly.spring_board_mybatis.board.dto.BoardListResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {
    void insertBoard(Board board);

    // 페이징 + 검색 조건 적용 게시글 조회
    List<BoardListResponse> searchBoard(@Param("boardTypeCode") String boardTypeCode,
                                        @Param("searchType") String searchType,
                                        @Param("searchKeyword") String searchKeyword,
                                        @Param("limit") int limit,
                                        @Param("offset") int offset,
                                        @Param("orderBy") String orderBy);

    // 전체 게시글 수 (페이징 계산용)
    int countBoard(@Param("boardTypeCode") String boardTypeCode,
                   @Param("searchType") String searchType,
                   @Param("searchKeyword") String searchKeyword);
}
