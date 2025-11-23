package com.melly.spring_board_mybatis.auth.security;

import com.melly.spring_board_mybatis.common.exception.CustomException;
import com.melly.spring_board_mybatis.common.exception.ErrorType;
import com.melly.spring_board_mybatis.member.domain.Member;
import com.melly.spring_board_mybatis.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = Optional.ofNullable(memberMapper.findByUsername(username))
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND, "해당 회원은 존재하지 않습니다."));

        return new PrincipalDetails(member);
    }
}
