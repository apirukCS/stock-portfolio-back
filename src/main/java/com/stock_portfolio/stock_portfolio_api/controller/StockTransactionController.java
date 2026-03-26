package com.stock_portfolio.stock_portfolio_api.controller;

import com.stock_portfolio.stock_portfolio_api.common.JwtUtil;
import com.stock_portfolio.stock_portfolio_api.dto.StockDto;
import com.stock_portfolio.stock_portfolio_api.dto.request.CreateStockRequest;
import com.stock_portfolio.stock_portfolio_api.dto.request.CreateStockTransactionRequest;
import com.stock_portfolio.stock_portfolio_api.dto.response.PaginationResponse;
import com.stock_portfolio.stock_portfolio_api.dto.response.StockTransactionDto;
import com.stock_portfolio.stock_portfolio_api.dto.response.TransactionSummary;
import com.stock_portfolio.stock_portfolio_api.service.StockTransactionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stock-transactions")
@RequiredArgsConstructor
public class StockTransactionController {

    @Autowired
    StockTransactionService stockTransactionService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<StockTransactionDto> createTransaction(@Valid @RequestBody CreateStockTransactionRequest request, HttpServletRequest headerRequest) {
        Long userId = jwtUtil.getUserIdFromHeaderRequest(headerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(stockTransactionService.createTransaction(userId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockTransactionDto> updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody CreateStockTransactionRequest request,
            HttpServletRequest headerRequest
    ) {
        Long userId = jwtUtil.getUserIdFromHeaderRequest(headerRequest);
        StockTransactionDto updated = stockTransactionService.updateTransaction(userId, id, request);
        return ResponseEntity.ok(updated);
    }


    @GetMapping
    public ResponseEntity<PaginationResponse> getTransactions(
            HttpServletRequest headerRequest,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long stockId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) List<String> transactionTypes
    ) {
        Long userId = jwtUtil.getUserIdFromHeaderRequest(headerRequest);

        LocalDate startDateTime = null;
        if (startDate != null) {
            startDateTime = LocalDate.parse(startDate);
        }

        LocalDate endDateTime = null;
        if (endDate != null) {

            endDateTime = LocalDate.parse(endDate);
        }

        Page<StockTransactionDto> result = stockTransactionService.getTransactionsByUserId(page, size, userId, stockId, startDateTime, endDateTime, transactionTypes);

        return ResponseEntity.ok(PaginationResponse.builder()
                .items(result.getContent())
                .page(result.getNumber() + 1)
                .totalItem(result.getTotalElements())
                .totalPage(result.getTotalPages())
                .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StockTransactionDto> deleteTransaction(@PathVariable Long id, HttpServletRequest headerRequest) {
        stockTransactionService.deleteStockTransactionById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary")
    public ResponseEntity<TransactionSummary> getSummary(HttpServletRequest headerRequest) {
        Long userId = jwtUtil.getUserIdFromHeaderRequest(headerRequest);
        TransactionSummary summary = stockTransactionService.getAlleSummaryTransactionsByUserId(userId);
        return ResponseEntity.ok(summary);
    }
}
