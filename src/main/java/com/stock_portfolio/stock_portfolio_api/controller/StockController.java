package com.stock_portfolio.stock_portfolio_api.controller;

import com.stock_portfolio.stock_portfolio_api.common.JwtUtil;
import com.stock_portfolio.stock_portfolio_api.dto.StockDto;
import com.stock_portfolio.stock_portfolio_api.dto.request.CreateStockRequest;
import com.stock_portfolio.stock_portfolio_api.exception.BadRequestException;
import com.stock_portfolio.stock_portfolio_api.service.StockService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    @Autowired
    StockService stockService;

    @Autowired
    private JwtUtil jwtUtils;

    @PostMapping
    public ResponseEntity<StockDto> createStock(@Valid @RequestBody CreateStockRequest request, HttpServletRequest headerRequest) {
        Long userId = jwtUtils.getUserIdFromHeaderRequest(headerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(stockService.createStock(userId, request.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockDto> updateStockById(@Valid @RequestBody CreateStockRequest request, @PathVariable Long id, HttpServletRequest headerRequest) {
        Long userId = jwtUtils.getUserIdFromHeaderRequest(headerRequest);
        return ResponseEntity.status(HttpStatus.OK).body(stockService.updateStockById(userId, request.getName(), id));
    }

    @GetMapping
    public ResponseEntity<List<StockDto>> getStocks(HttpServletRequest headerRequest) {
        Long userId = jwtUtils.getUserIdFromHeaderRequest(headerRequest);
        List<StockDto> stocks = stockService.getStocksByUserId(userId);
        return ResponseEntity.ok(stocks);
    }
}
