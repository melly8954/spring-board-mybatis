package com.spBoard.spring_board_mybatis.member.service;

import com.spBoard.spring_board_mybatis.common.exception.CustomException;
import com.spBoard.spring_board_mybatis.common.exception.ErrorType;
import com.spBoard.spring_board_mybatis.member.domain.Member;
import com.spBoard.spring_board_mybatis.member.domain.MemberRole;
import com.spBoard.spring_board_mybatis.member.domain.MemberStatus;
import com.spBoard.spring_board_mybatis.member.dto.CreateMemberRequest;
import com.spBoard.spring_board_mybatis.member.dto.CreateMemberResponse;
import com.spBoard.spring_board_mybatis.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public CreateMemberResponse createMember(CreateMemberRequest dto, MultipartFile file) {
        if(memberMapper.findByUsername(dto.getUsername()) != null){
            throw new CustomException(ErrorType.BAD_REQUEST, "이미 사용 중인 username 입니다.");
        }
        if(!dto.getPassword().equals(dto.getConfirmPassword())){
            throw new CustomException(ErrorType.BAD_REQUEST, "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        Member member = Member.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .role(MemberRole.USER)
                .status(MemberStatus.ACTIVE)
                .build();
        memberMapper.insertMember(member);

        return CreateMemberResponse.builder()
                .memberId(member.getMemberId())
                .username(member.getUsername())
                .name(member.getName())
                .profileImageUrl(null)
                .createdAt(member.getCreatedAt())
                .build();
    }

    @Override
    public Member findById(Long memberId) {
        return memberMapper.findById(memberId);
    }

    @Override
    public Member findByUsername(String username) {
        return memberMapper.findByUsername(username);
    }
}
