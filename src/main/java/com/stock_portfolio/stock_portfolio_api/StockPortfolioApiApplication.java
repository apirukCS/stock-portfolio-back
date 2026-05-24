package com.stock_portfolio.stock_portfolio_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StockPortfolioApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockPortfolioApiApplication.class, args);
	}

}
