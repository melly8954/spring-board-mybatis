package com.melly.spring_board_mybatis.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.melly.spring_board_mybatis.filestorage.domain.FileDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class UpdateBoardResponse {
    private Long boardId;
    private String title;
    private String content;
    private List<FileDto> files;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
