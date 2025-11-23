package com.spBoard.spring_board_mybatis.filestorage.mapper;

import com.spBoard.spring_board_mybatis.filestorage.domain.FileDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
    void insertFile(FileDto file);
}
