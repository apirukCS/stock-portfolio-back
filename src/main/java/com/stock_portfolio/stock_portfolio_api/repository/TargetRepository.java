package com.stock_portfolio.stock_portfolio_api.repository;

import com.stock_portfolio.stock_portfolio_api.entity.Target;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TargetRepository extends JpaRepository<Target, Long> {

    Optional<Target> findByUserId(Long userId);

}