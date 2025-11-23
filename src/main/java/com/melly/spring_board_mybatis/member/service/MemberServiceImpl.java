package com.melly.spring_board_mybatis.member.service;

import com.melly.spring_board_mybatis.common.exception.CustomException;
import com.melly.spring_board_mybatis.common.exception.ErrorType;
import com.melly.spring_board_mybatis.filestorage.domain.FileDto;
import com.melly.spring_board_mybatis.filestorage.mapper.FileMapper;
import com.melly.spring_board_mybatis.filestorage.service.FileService;
import com.melly.spring_board_mybatis.member.domain.Member;
import com.melly.spring_board_mybatis.member.domain.MemberRole;
import com.melly.spring_board_mybatis.member.domain.MemberStatus;
import com.melly.spring_board_mybatis.member.dto.CreateMemberRequest;
import com.melly.spring_board_mybatis.member.dto.CreateMemberResponse;
import com.melly.spring_board_mybatis.member.dto.MemberDto;
import com.melly.spring_board_mybatis.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;
    private final FileMapper fileMapper;

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

        String profileImageUrl = "";
        if (file != null && !file.isEmpty()) {
            profileImageUrl = fileService.saveFile(file, "member");

            FileDto fileMeta = FileDto.builder()
                    .relatedType("member")               // 어떤 엔티티와 연관된 파일인지
                    .relatedId(member.getMemberId())      // 방금 생성된 멤버 ID
                    .originalName(file.getOriginalFilename())
                    .uniqueName(profileImageUrl.substring(profileImageUrl.lastIndexOf("/") + 1))
                    .filePath(profileImageUrl)
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .fileOrder(1)
                    .build();
            fileMapper.insertFile(fileMeta);
        }

        return CreateMemberResponse.builder()
                .memberId(member.getMemberId())
                .username(member.getUsername())
                .name(member.getName())
                .profileImageUrl(profileImageUrl)
                .createdAt(member.getCreatedAt())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public MemberDto getCurrentMember(Long memberId) {
        Member member = Optional.ofNullable(memberMapper.findById(memberId))
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND, "현재 접속 중인 사용자가 존재하지 않습니다."));
        FileDto file = Optional.ofNullable(fileMapper.findByRelatedTypeAndRelatedId("member", memberId))
                .orElse(null);

        return MemberDto.builder()
                .memberId(member.getMemberId())
                .username(member.getUsername())
                .name(member.getName())
                .profileImage(file != null ? file.getFilePath() : null)
                .build();
    }

}
