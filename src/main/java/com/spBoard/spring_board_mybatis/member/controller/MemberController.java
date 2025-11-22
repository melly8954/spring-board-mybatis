package com.spBoard.spring_board_mybatis.member.controller;

import com.spBoard.spring_board_mybatis.common.controller.ResponseController;
import com.spBoard.spring_board_mybatis.common.dto.ResponseDto;
import com.spBoard.spring_board_mybatis.common.trace.RequestTraceIdFilter;
import com.spBoard.spring_board_mybatis.member.dto.CreateMemberRequest;
import com.spBoard.spring_board_mybatis.member.dto.CreateMemberResponse;
import com.spBoard.spring_board_mybatis.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
}
