package com.whiz.app.boot.interfaces.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
public class SimplePageData<D> {
    private Integer pageNumber;
    private Integer numberOfElements;
    private Integer totalPages;
    private Long totalElements;
    private List<D> content;

    public static <D> SimplePageData<D> of(Page<D> pageable) {
        return (SimplePageData<D>) SimplePageData.builder()
            .pageNumber(pageable.getNumber())
            .numberOfElements(pageable.getNumberOfElements())
            .totalPages(pageable.getTotalPages())
            .totalElements(pageable.getTotalElements())
            .content((List<Object>) pageable.getContent())
            .build();
    }
}