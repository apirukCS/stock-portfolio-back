package com.stock_portfolio.stock_portfolio_api.dto.response;

import lombok.Data;

@Data
public class FinnhubNewsDto {

    private String category;

    private Long datetime;

    private String headline;

    private Long id;

    private String image;

    private String related;

    private String source;

    private String summary;

    private String url;
}
