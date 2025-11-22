package com.spBoard.spring_board_mybatis.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SearchParamDto {
    private int page;
    private int size;
    private String sortBy;      // 정렬 기준 컬럼
    private String sortOrder;   // asc or desc

    /**
     * MyBatis SQL에서 사용할 OFFSET 계산
     */
    public int getOffset() {
        return Math.max(page - 1, 0) * size;
    }

    /**
     * MyBatis SQL에서 사용할 ORDER BY 문자열
     */
    public String getOrderBy() {
        String sortField = (sortBy == null || sortBy.isBlank()) ? "created_at" : sortBy;
        String direction = ("asc".equalsIgnoreCase(sortOrder)) ? "ASC" : "DESC";
        return sortField + " " + direction;
    }

    /**
     * MyBatis LIMIT 값
     */
    public int getLimit() {
        return size;
    }
}
