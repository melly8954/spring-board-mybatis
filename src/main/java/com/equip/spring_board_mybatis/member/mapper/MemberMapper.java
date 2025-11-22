package com.equip.spring_board_mybatis.member.mapper;

import com.equip.spring_board_mybatis.member.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    Member findById(Long memberId);
    Member findByUsername(String username);
    List<Member> findAll();
    void insertMember(Member member);
    void updateMember(Member member);
    void deleteMember(Long memberId);
}
