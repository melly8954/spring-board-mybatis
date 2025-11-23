package com.melly.spring_board_mybatis.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.melly.spring_board_mybatis.board.domain.BoardStatus;
import com.melly.spring_board_mybatis.filestorage.domain.FileDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class CreateBoardResponse {
    private Long boardId;
    private String boardType;
    private String title;
    private String content;
    private BoardStatus status;
    private List<FileDto> files;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
