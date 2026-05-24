package com.stock_portfolio.stock_portfolio_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "stock_news")
@Getter
@Setter
public class StockNews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    @Column(length = 2000)
    private String headline;

    @Column(length = 5000)
    private String summary;

    private String image;

    @Column(unique = true, length = 2000)
    private String url;

    private String source;

    private Long datetime;
}
