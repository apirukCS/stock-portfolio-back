package com.stock_portfolio.stock_portfolio_api.service;

import com.stock_portfolio.stock_portfolio_api.dto.response.FinnhubNewsDto;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinnhubService {

    private final WebClient webClient;

    @Value("${finnhub.api.key}")
    private String apiKey;

    public List<FinnhubNewsDto> getCompanyNews(String symbol) {

        LocalDate today = LocalDate.now();
        LocalDate past = today.minusDays(30);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/company-news")
                        .queryParam("symbol", symbol)
                        .queryParam("from", past)
                        .queryParam("to", today)
                        .queryParam("token", apiKey)
                        .build())
                .retrieve()
                .bodyToFlux(FinnhubNewsDto.class)
                .sort((a, b) -> Long.compare(b.getDatetime(), a.getDatetime()))
                .take(10)
                .collectList()
                .block();
    }
}
