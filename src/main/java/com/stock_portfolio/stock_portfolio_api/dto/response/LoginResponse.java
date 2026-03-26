package com.stock_portfolio.stock_portfolio_api.dto.response;

import lombok.Data;

@Data
public class LoginResponse {
    private Long userId;
    private String email;
    private String name;
    private String accessToken;
}