package com.stock_portfolio.stock_portfolio_api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class CreateStockTransactionRequest {
    @NotNull(message = "Stock ID ต้องระบุ")
    @Min(value = 1, message = "Stock ID ต้องมากกว่า 0")
    private Long stockId;

    @NotNull(message = "วันที่ทำรายการต้องระบุ")
    private LocalDateTime transactionDate;

    @NotBlank(message = "ประเภทรายการต้องระบุ (BUY/SELL)")
    private String transactionType;

    @NotNull(message = "ราคารวมต้องระบุ")
    @Min(value = 0, message = "ราคาต้องไม่ติดลบ")
    private BigDecimal price;

    @NotNull(message = "จำนวนหน่วยต้องระบุ")
    private String priceUnit;

    private BigDecimal vat;
    private String vatUnit;
    private BigDecimal commission;
    private String commissionUnit;
    private BigDecimal exchangeRate;
    private String reason;
}

