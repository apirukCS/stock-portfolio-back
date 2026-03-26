package com.stock_portfolio.stock_portfolio_api.service;

import com.stock_portfolio.stock_portfolio_api.dto.StockDto;
import com.stock_portfolio.stock_portfolio_api.entity.Stock;
import com.stock_portfolio.stock_portfolio_api.exception.BadRequestException;
import com.stock_portfolio.stock_portfolio_api.exception.ResourceNotFoundException;
import com.stock_portfolio.stock_portfolio_api.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StockService {

    @Autowired
    StockRepository stockRepository;

    public List<StockDto> getStocksByUserId(Long userId) {
        if (userId == null) {
            throw new BadRequestException("กรุณาระบุข้อมูลผู้ใช้งาน");
        }
        return stockRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public StockDto createStock(Long userId, String name) {
        if (stockRepository.findByUserIdAndName(userId, name).isPresent()) {
            throw new BadRequestException("ชื่อหุ้น '" + name + "' มีอยู่แล้ว");
        }
        Stock stock = Stock.builder()
                .userId(userId)
                .name(name)
                .build();
        return toDto(stockRepository.save(stock));
    }

    public StockDto updateStockById(Long userId, String name, Long stockId) {
        var stock = stockRepository.findById(stockId);
        if(stock.isEmpty()) {
            throw new ResourceNotFoundException("ไม่พบข้อมูลหุ้นที่เลือก");
        }

        if (!Objects.equals(stock.get().getUserId(), userId) && Objects.equals(stock.get().getName(), name)) {
            throw new BadRequestException("ชื่อหุ้น '" + name + "' มีอยู่แล้ว");
        }

        stock.ifPresent(s-> s.setName(name));
        return toDto(stockRepository.save(stock.get()));
    }

    private StockDto toDto(Stock stock) {
        return StockDto.builder()
                .id(stock.getId())
                .name(stock.getName())
                .build();
    }
}
