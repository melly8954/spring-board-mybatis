package com.spBoard.spring_board_mybatis.member.service;

import com.spBoard.spring_board_mybatis.member.domain.Member;
import com.spBoard.spring_board_mybatis.member.dto.CreateMemberRequest;
import com.spBoard.spring_board_mybatis.member.dto.CreateMemberResponse;
import com.spBoard.spring_board_mybatis.member.dto.MemberDto;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {
    CreateMemberResponse createMember(CreateMemberRequest dto, MultipartFile file);
    MemberDto getCurrentMember(Long memberId);
}
