package com.melly.spring_board_mybatis.member.controller;

import com.melly.spring_board_mybatis.auth.security.PrincipalDetails;
import com.melly.spring_board_mybatis.common.controller.ResponseController;
import com.melly.spring_board_mybatis.common.dto.ResponseDto;
import com.melly.spring_board_mybatis.common.trace.RequestTraceIdFilter;
import com.melly.spring_board_mybatis.member.dto.CreateMemberRequest;
import com.melly.spring_board_mybatis.member.dto.CreateMemberResponse;
import com.melly.spring_board_mybatis.member.dto.MemberDto;
import com.melly.spring_board_mybatis.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController implements ResponseController {
    private final MemberService memberService;

    @PostMapping("")
    public ResponseEntity<ResponseDto<CreateMemberResponse>> createMember(@RequestPart(value = "data") CreateMemberRequest dto,
                                                                          @RequestPart(value = "file", required = false) MultipartFile file){
        String traceId = RequestTraceIdFilter.getTraceId();
        log.info("[회원가입 요청 API] TraceId={}", traceId);

        CreateMemberResponse result = memberService.createMember(dto, file);

        return makeResponseEntity(traceId, HttpStatus.OK, null, "회원가입 요청 성공", result);
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseDto<MemberDto>> getCurrentMember(@AuthenticationPrincipal PrincipalDetails principal){
        String traceId = RequestTraceIdFilter.getTraceId();
        log.info("[현재 회원 정보 조회 요청 API] TraceId={}", traceId);

        MemberDto result = memberService.getCurrentMember(principal.getMember().getMemberId());

        return makeResponseEntity(traceId, HttpStatus.OK, null, "현재 사용자 정보 조회 요청", result);
    }
}
