package com.stock_portfolio.stock_portfolio_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfo {
    private String email;
    private String name;
    private String googleId;
}