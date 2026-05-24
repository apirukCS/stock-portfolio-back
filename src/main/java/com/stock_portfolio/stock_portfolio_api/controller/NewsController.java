package com.stock_portfolio.stock_portfolio_api.controller;

import com.stock_portfolio.stock_portfolio_api.entity.StockNews;
import com.stock_portfolio.stock_portfolio_api.repository.StockNewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final StockNewsRepository repository;

    @GetMapping
    public List<StockNews> getNews() {
        return repository.findTop100ByOrderByDatetimeDesc();
    }
}
