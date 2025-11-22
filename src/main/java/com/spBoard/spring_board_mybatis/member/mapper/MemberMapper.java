package com.spBoard.spring_board_mybatis.member.mapper;

import com.spBoard.spring_board_mybatis.member.domain.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void insertMember(Member member);
    Member findById(Long memberId);
    Member findByUsername(String username);
}
