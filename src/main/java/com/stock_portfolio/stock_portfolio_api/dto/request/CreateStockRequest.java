package com.stock_portfolio.stock_portfolio_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateStockRequest {
    @NotBlank(message = "Stock name is required")
    @Size(max = 255, message = "Stock name must be max 255 characters")
    private String name;
}

