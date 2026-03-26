package com.stock_portfolio.stock_portfolio_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSummary {
    private BigDecimal totalBuy;
    private BigDecimal totalSell;
    private BigDecimal totalVat;
    private BigDecimal totalCommission;
}


