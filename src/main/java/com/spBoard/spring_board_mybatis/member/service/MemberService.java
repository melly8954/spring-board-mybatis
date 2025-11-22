package com.spBoard.spring_board_mybatis.member.service;

import com.spBoard.spring_board_mybatis.member.domain.Member;
import com.spBoard.spring_board_mybatis.member.dto.CreateMemberRequest;
import com.spBoard.spring_board_mybatis.member.dto.CreateMemberResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {
    CreateMemberResponse createMember(CreateMemberRequest dto, MultipartFile file);
    Member findById(Long memberId);
    Member findByUsername(String username);
}
