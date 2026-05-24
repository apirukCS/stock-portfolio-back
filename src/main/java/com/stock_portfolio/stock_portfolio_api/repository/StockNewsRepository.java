package com.stock_portfolio.stock_portfolio_api.repository;

import com.stock_portfolio.stock_portfolio_api.entity.StockNews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockNewsRepository extends JpaRepository<StockNews, Long> {
    boolean existsByUrl(String url);
    List<StockNews> findTop30ByOrderByDatetimeDesc();
}
