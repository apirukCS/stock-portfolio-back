package com.stock_portfolio.stock_portfolio_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockTransactionDto {
    private Long id;
    private Long userId;
    private Long stockId;
    private String stockName;
    private LocalDateTime transactionDate;
    private String transactionType;
    private BigDecimal price;
    private String priceUnit;
    private BigDecimal vat;
    private String vatUnit;
    private BigDecimal commission;
    private String commissionUnit;
    private BigDecimal exchangeRate;
    private String reason;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

