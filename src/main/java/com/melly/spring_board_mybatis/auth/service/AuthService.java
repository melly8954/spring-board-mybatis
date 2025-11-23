package com.melly.spring_board_mybatis.auth.service;

import com.melly.spring_board_mybatis.auth.dto.LoginRequest;
import com.melly.spring_board_mybatis.auth.dto.LoginResponse;
import com.melly.spring_board_mybatis.auth.dto.ReIssueTokenDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    LoginResponse login(LoginRequest dto, HttpServletResponse response);
    ReIssueTokenDto reissueToken(HttpServletRequest request, HttpServletResponse response);
    void logout(HttpServletRequest request, HttpServletResponse response);
}
