package com.melly.spring_board_mybatis.board.service;

import com.melly.spring_board_mybatis.board.domain.Board;
import com.melly.spring_board_mybatis.board.domain.BoardStatus;
import com.melly.spring_board_mybatis.board.domain.BoardType;
import com.melly.spring_board_mybatis.board.dto.*;
import com.melly.spring_board_mybatis.board.mapper.BoardMapper;
import com.melly.spring_board_mybatis.board.mapper.BoardTypeMapper;
import com.melly.spring_board_mybatis.common.dto.PageResponseDto;
import com.melly.spring_board_mybatis.common.dto.SearchParamDto;
import com.melly.spring_board_mybatis.common.exception.CustomException;
import com.melly.spring_board_mybatis.common.exception.ErrorType;
import com.melly.spring_board_mybatis.filestorage.domain.FileDto;
import com.melly.spring_board_mybatis.filestorage.mapper.FileMapper;
import com.melly.spring_board_mybatis.filestorage.service.FileService;
import com.melly.spring_board_mybatis.member.domain.Member;
import com.melly.spring_board_mybatis.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final MemberMapper memberMapper;
    private final BoardMapper boardMapper;
    private final BoardTypeMapper boardTypeMapper;
    private final FileService fileService;
    private final FileMapper fileMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String VIEW_KEY_PREFIX = "board:viewed:";

    @Override
    @Transactional
    public CreateBoardResponse createBoard(CreateBoardRequest dto, List<MultipartFile> files, Long memberId) {
        Member writer = Optional.ofNullable(memberMapper.findById(memberId))
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND, "해당 사용자는 존재하지 않습니다."));
        BoardType boardType = Optional.ofNullable(boardTypeMapper.findById(dto.getBoardTypeId()))
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND, "해당 게시판 타입은 존재하지 않습니다."));

        Board board = Board.builder()
                .boardType(boardType)
                .writer(writer)
                .title(dto.getTitle())
                .content(dto.getContent())
                .viewCount(0)
                .likeCount(0)
                .status(BoardStatus.ACTIVE)
                .build();
        boardMapper.insertBoard(board);

        List<FileDto> savedFiles = new ArrayList<>();
        // null 체크 + 빈 파일 제거
        if (files == null) {
            files = List.of();
        } else {
            files = files.stream()
                    .filter(f -> f != null && !f.isEmpty())
                    .toList();
        }

        if (!files.isEmpty()) {
            int fileOrder = 0;
            String typeKey = "board_" + boardType.getName();
            List<String> fileUrls = fileService.saveFiles(files, typeKey);

            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                String url = fileUrls.get(i); // fileService에서 생성한 접근 URL

                FileDto meta = FileDto.builder()
                        .relatedType(typeKey)
                        .relatedId(board.getBoardId())
                        .originalName(file.getOriginalFilename())
                        .uniqueName(url.substring(url.lastIndexOf("/") + 1)) // URL 에서 uniqueName 추출
                        .fileOrder(fileOrder++)
                        .fileType(file.getContentType())
                        .filePath(url) // 접근 URL
                        .fileSize(file.getSize())
                        .build();
                savedFiles.add(meta);
            }
            fileMapper.insertAllFiles(savedFiles);
        }

        List<FileDto> fileDtoList = savedFiles.stream()
                .map(f -> FileDto.builder()
                        .fileId(f.getFileId())
                        .relatedType(f.getRelatedType())
                        .relatedId(f.getRelatedId())
                        .originalName(f.getOriginalName())
                        .uniqueName(f.getUniqueName())
                        .fileOrder(f.getFileOrder())
                        .filePath(f.getFilePath())
                        .fileType(f.getFileType())
                        .fileSize(f.getFileSize())
                        .createdAt(f.getCreatedAt())
                        .build())
                .toList();

        return CreateBoardResponse.builder()
                .boardId(board.getBoardId())
                .boardType(boardType.getName())
                .title(dto.getTitle())
                .content(dto.getContent())
                .status(BoardStatus.ACTIVE)
                .files(fileDtoList)
                .createdAt(board.getCreatedAt())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponseDto<BoardListResponse> searchBoard(BoardFilter filter) {
        SearchParamDto param = new SearchParamDto(
                filter.getPage(),
                filter.getSize(),
                filter.getSortBy(),
                filter.getSortOrder()
        );

        List<BoardListResponse> content = boardMapper.searchBoard(
                filter.getBoardTypeCode(),
                filter.getSearchType(),
                filter.getSearchKeyword(),
                filter.getLimit(),
                filter.getOffset(),
                filter.getOrderBy()
        );

        int total = boardMapper.countBoard(
                filter.getBoardTypeCode(),
                filter.getSearchType(),
                filter.getSearchKeyword()
        );

        return PageResponseDto.<BoardListResponse>builder()
                .content(content)
                .page(param.getPage())
                .size(param.getSize())
                .totalElements(total)
                .totalPages((total + param.getSize() - 1) / param.getSize())
                .numberOfElements(content.size())
                .first(param.getPage() == 1)
                .last(param.getPage() * param.getSize() >= total)
                .empty(content.isEmpty())
                .build();
    }

    @Override
    public BoardResponse getBoard(Long boardId, Long currentUserId) {
        return null;
    }

    @Override
    public UpdateBoardResponse updateBoard(Long boardId, UpdateBoardRequest dto, List<MultipartFile> newFiles, Long currentUserId) {
        return null;
    }

    @Override
    public void softDeleteBoard(Long boardId, Long currentUserId) {

    }
}
