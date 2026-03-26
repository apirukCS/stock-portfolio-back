package com.stock_portfolio.stock_portfolio_api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginationResponse {
    private List<StockTransactionDto> items;
    private Integer page;
    private Long totalItem;
    private Integer totalPage;
}
