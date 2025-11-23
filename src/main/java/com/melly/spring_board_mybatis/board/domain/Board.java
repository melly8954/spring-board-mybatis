package com.melly.spring_board_mybatis.board.domain;

import com.melly.spring_board_mybatis.board.dto.UpdateBoardRequest;
import com.melly.spring_board_mybatis.common.exception.CustomException;
import com.melly.spring_board_mybatis.common.exception.ErrorType;
import com.melly.spring_board_mybatis.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board{
    private Long boardId;
    private BoardType boardType;
    private Member writer;

    private String title;
    private String content;
    private Integer viewCount;
    private Integer likeCount;
    private BoardStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
