package com.spBoard.spring_board_mybatis.filestorage.mapper;

import com.spBoard.spring_board_mybatis.filestorage.domain.FileDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FileMapper {
    void insertFile(FileDto file);
    FileDto findByRelatedTypeAndRelatedId(@Param("relatedType") String relatedType, @Param("relatedId") Long relatedId);
}
