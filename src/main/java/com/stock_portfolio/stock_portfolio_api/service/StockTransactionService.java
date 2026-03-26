package com.stock_portfolio.stock_portfolio_api.service;

import com.stock_portfolio.stock_portfolio_api.common.JwtUtil;
import com.stock_portfolio.stock_portfolio_api.dto.request.CreateStockTransactionRequest;
import com.stock_portfolio.stock_portfolio_api.dto.response.StockTransactionDto;
import com.stock_portfolio.stock_portfolio_api.dto.response.TransactionSummary;
import com.stock_portfolio.stock_portfolio_api.entity.Stock;
import com.stock_portfolio.stock_portfolio_api.entity.StockTransaction;
import com.stock_portfolio.stock_portfolio_api.exception.BadRequestException;
import com.stock_portfolio.stock_portfolio_api.exception.ResourceNotFoundException;
import com.stock_portfolio.stock_portfolio_api.repository.StockRepository;
import com.stock_portfolio.stock_portfolio_api.repository.StockTransactionRepository;
import com.stock_portfolio.stock_portfolio_api.specification.StockTransactionSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockTransactionService {

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public StockTransactionDto createTransaction(Long userId, CreateStockTransactionRequest request) {
        Stock stock = stockRepository.findById(request.getStockId()).orElseThrow(() -> new BadRequestException("Stock not found: " + request.getStockId()));
        StockTransaction transaction = StockTransaction.builder()
                .userId(userId)
                .stock(stock)
                .transactionDate(LocalDateTime.now())
                .transactionType(request.getTransactionType())
                .price(request.getPrice())
                .priceUnit(request.getPriceUnit())
                .vat(request.getVat())
                .vatUnit(request.getVatUnit())
                .commission(request.getCommission())
                .commissionUnit(request.getCommissionUnit())
                .exchangeRate(request.getExchangeRate())
                .reason(request.getReason())
                .status("ACTIVE")
                .build();

        StockTransaction saved = stockTransactionRepository.save(transaction);
        return toDto(saved);
    }

    public StockTransactionDto updateTransaction(Long userId, Long id, CreateStockTransactionRequest request) {
        StockTransaction transaction = stockTransactionRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        // Update fields
        transaction.setTransactionType(request.getTransactionType());
        transaction.setPrice(request.getPrice());
        transaction.setPriceUnit(request.getPriceUnit());
        transaction.setVat(request.getVat());
        transaction.setVatUnit(request.getVatUnit());
        transaction.setCommission(request.getCommission());
        transaction.setCommissionUnit(request.getCommissionUnit());
        transaction.setExchangeRate(request.getExchangeRate());
        transaction.setReason(request.getReason());
        transaction.setUpdatedAt(LocalDateTime.now());

        StockTransaction saved = stockTransactionRepository.save(transaction);
        return toDto(saved);
    }

    public void deleteStockTransactionById(Long id) {
        stockTransactionRepository.deleteById(id);
    }


//    public Page<StockTransactionDto> getTransactionsByUserId(Long userId, int page, int size) {
//        Pageable pageable = PageRequest.of(Math.max(0, page - 1), size);
//        Page<StockTransaction> transactions = stockTransactionRepository.findByUserIdOrderByTransactionDateDesc(userId, pageable);
//
//        return transactions.map(this::toDto); // ← map ไป DTO
//    }

    public Page<StockTransactionDto> getTransactionsByUserId(
            int page,
            int size,
            Long userId,
            Long stockId,
            LocalDate startDate,
            LocalDate endDate,
            List<String> transactionTypes
    ) {
        return getStockTransactionSpecification(page, size, userId, stockId, startDate, endDate, transactionTypes).map(this::toDto);
    }

    public TransactionSummary getAlleSummaryTransactionsByUserId(Long userId) {
        List<StockTransaction> stockTransactions = stockTransactionRepository.findByUserId(userId);
        return calculateSummaryFromTransactions(stockTransactions);
    }

    private StockTransactionDto toDto(StockTransaction entity) {
        return StockTransactionDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .stockId(entity.getStock().getId())
                .stockName(entity.getStock().getName())
                .transactionDate(entity.getTransactionDate())
                .transactionType(entity.getTransactionType())
                .price(entity.getPrice())
                .priceUnit(entity.getPriceUnit())
                .vat(entity.getVat())
                .vatUnit(entity.getVatUnit())
                .commission(entity.getCommission())
                .commissionUnit(entity.getCommissionUnit())
                .exchangeRate(entity.getExchangeRate())
                .reason(entity.getReason())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private Page<StockTransaction> getStockTransactionSpecification(int page, int size, Long userId, Long stockId, LocalDate startDate, LocalDate endDate, List<String> transactionTypes) {
        Pageable pageable = PageRequest.of(Math.max(0, page - 1), size, Sort.by(Sort.Direction.DESC, "transactionDate"));

        Specification<StockTransaction> spec =
                Specification.where(StockTransactionSpecification.hasUserId(userId))
                        .and(StockTransactionSpecification.hasStockId(stockId))
                        .and(StockTransactionSpecification.startDate(startDate))
                        .and(StockTransactionSpecification.endDate(endDate))
                        .and(StockTransactionSpecification.hasTransactionTypes(transactionTypes));

        return stockTransactionRepository.findAll(spec, pageable);
    }

    private TransactionSummary calculateSummaryFromTransactions(List<StockTransaction> transactions) {
        BigDecimal totalBuyThb = BigDecimal.ZERO;
        BigDecimal totalSellThb = BigDecimal.ZERO;
        BigDecimal totalVatThb = BigDecimal.ZERO;
        BigDecimal totalCommissionThb = BigDecimal.ZERO;

        for (StockTransaction tx : transactions) {
            final BigDecimal EXCHANGE_RATE = tx.getExchangeRate();
            BigDecimal amountThb = "USD".equals(tx.getPriceUnit())
                    ? tx.getPrice().multiply(EXCHANGE_RATE)
                    : tx.getPrice();

            BigDecimal vatThb = tx.getVat() != null && "USD".equals(tx.getVatUnit())
                    ? tx.getVat().multiply(EXCHANGE_RATE)
                    : tx.getVat();

            BigDecimal commissionThb = tx.getCommission() != null && "USD".equals(tx.getCommissionUnit())
                    ? tx.getCommission().multiply(EXCHANGE_RATE)
                    : tx.getCommission();

            // ซื้อ
            if ("BUY".equals(tx.getTransactionType())) {
                totalBuyThb = totalBuyThb.add(amountThb);
            }
            // ขาย
            else if ("SELL".equals(tx.getTransactionType())) {
                totalSellThb = totalSellThb.add(amountThb);
            }

            // VAT
            if (tx.getVat() != null) {
                totalVatThb = totalVatThb.add(vatThb != null ? vatThb : BigDecimal.ZERO);
            }

            // Commission
            if (tx.getCommission() != null) {
                totalCommissionThb = totalCommissionThb.add(commissionThb != null ? commissionThb : BigDecimal.ZERO);
            }
        }

        return new TransactionSummary(totalBuyThb, totalSellThb, totalVatThb, totalCommissionThb);
    }


}
