package com.stock_portfolio.stock_portfolio_api.repository;

import com.stock_portfolio.stock_portfolio_api.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findByUserIdOrderByCreatedAtDesc(Long userId);
    Optional<Stock> findByUserIdAndName(Long userId, String name);
}

