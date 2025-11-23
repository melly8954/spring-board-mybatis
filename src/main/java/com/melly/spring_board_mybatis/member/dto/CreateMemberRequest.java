package com.melly.spring_board_mybatis.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CreateMemberRequest {
    private String username;
    private String password;
    private String confirmPassword;
    private String name;
}
