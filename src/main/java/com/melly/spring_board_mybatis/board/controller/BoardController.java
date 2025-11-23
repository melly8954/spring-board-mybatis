package com.melly.spring_board_mybatis.board.controller;

import com.melly.spring_board_mybatis.auth.security.PrincipalDetails;
import com.melly.spring_board_mybatis.board.dto.*;
import com.melly.spring_board_mybatis.board.service.BoardService;
import com.melly.spring_board_mybatis.common.controller.ResponseController;
import com.melly.spring_board_mybatis.common.dto.PageResponseDto;
import com.melly.spring_board_mybatis.common.dto.ResponseDto;
import com.melly.spring_board_mybatis.common.trace.RequestTraceIdFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards")
public class BoardController implements ResponseController {
    private final BoardService boardService;

    @PostMapping("")
    public ResponseEntity<ResponseDto<CreateBoardResponse>> createBoard(@RequestPart(value = "data") CreateBoardRequest dto,
                                                                        @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                                                        @AuthenticationPrincipal PrincipalDetails principal) {
        String traceId = RequestTraceIdFilter.getTraceId();
        log.info("[게시글 등록 요청 API] TraceId={}", traceId);

        CreateBoardResponse result = boardService.createBoard(dto, files, principal.getMember().getMemberId());

        return makeResponseEntity(traceId, HttpStatus.OK, null, "게시글 등록 성공", result);
    }

    @GetMapping("")
    public ResponseEntity<ResponseDto<PageResponseDto<BoardListResponse>>> searchBoard(@ModelAttribute BoardFilter filter) {
        String traceId = RequestTraceIdFilter.getTraceId();
        log.info("[게시글 목록 조회 요청 API] TraceId={}", traceId);

        PageResponseDto<BoardListResponse> result = boardService.searchBoard(filter);

        return makeResponseEntity(traceId, HttpStatus.OK, null, "게시글 목록 조회 성공", result);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ResponseDto<BoardResponse>> getBoard(@PathVariable Long boardId,
                                                               @AuthenticationPrincipal PrincipalDetails principal) {
        String traceId = RequestTraceIdFilter.getTraceId();
        log.info("[게시글 상세 조회 요청 API] TraceId={}", traceId);

        BoardResponse result = boardService.getBoard(boardId, principal.getMember().getMemberId());

        return makeResponseEntity(traceId, HttpStatus.OK, null, "게시글 상세 조회 성공", result);
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<ResponseDto<UpdateBoardResponse>> updateBoard(@PathVariable Long boardId,
                                                                        @RequestPart(value = "data") UpdateBoardRequest dto,
                                                                        @RequestPart(value = "files", required = false) List<MultipartFile> newFiles,
                                                                        @AuthenticationPrincipal PrincipalDetails principal) {
        String traceId = RequestTraceIdFilter.getTraceId();
        log.info("[게시글 수정 요청 API] TraceId={}", traceId);

        UpdateBoardResponse result = boardService.updateBoard(boardId, dto, newFiles, principal.getMember().getMemberId());

        return makeResponseEntity(traceId, HttpStatus.OK, null, "게시글 수정 성공", result);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<ResponseDto<Void>> softDeleteBoard(@PathVariable Long boardId,
                                                             @AuthenticationPrincipal PrincipalDetails principal) {
        String traceId = RequestTraceIdFilter.getTraceId();
        log.info("[게시글 삭제 요청 API] TraceId={}", traceId);

        boardService.softDeleteBoard(boardId, principal.getMember().getMemberId());

        return makeResponseEntity(traceId, HttpStatus.OK, null, "게시글 삭제 성공", null);
    }
}
