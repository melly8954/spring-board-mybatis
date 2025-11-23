package com.melly.spring_board_mybatis.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ReIssueTokenDto {
    private String newAccessToken;
    private String newRefreshToken;
}
