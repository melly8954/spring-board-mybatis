package com.melly.spring_board_mybatis.board.service;

import com.melly.spring_board_mybatis.board.dto.BoardTypeResponse;
import com.melly.spring_board_mybatis.board.mapper.BoardTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardTypeServiceImpl implements BoardTypeService {
    private final BoardTypeMapper boardTypeMapper;

    @Override
    @Transactional
    public List<BoardTypeResponse> getBoardTypes() {
        return boardTypeMapper.findAll()
                .stream()
                .map(boardType -> BoardTypeResponse.builder()
                        .boardTypeId(boardType.getBoardTypeId())
                        .boardTypeCode(boardType.getCode())
                        .boardTypeName(boardType.getName())
                        .build())
                .toList();
    }
}
