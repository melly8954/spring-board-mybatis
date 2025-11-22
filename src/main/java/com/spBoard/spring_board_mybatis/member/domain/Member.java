package com.spBoard.spring_board_mybatis.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class Member {
    private Long memberId;
    private String username;
    private String password;
    private String name;
    private MemberRole role;
    private MemberStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
