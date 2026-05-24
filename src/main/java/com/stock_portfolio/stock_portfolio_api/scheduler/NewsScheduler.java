package com.stock_portfolio.stock_portfolio_api.scheduler;

import com.stock_portfolio.stock_portfolio_api.dto.response.FinnhubNewsDto;
import com.stock_portfolio.stock_portfolio_api.entity.StockNews;
import com.stock_portfolio.stock_portfolio_api.repository.StockNewsRepository;
import com.stock_portfolio.stock_portfolio_api.service.FinnhubService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsScheduler {

    private final FinnhubService finnhubService;
    private final StockNewsRepository repository;

    private final List<String> symbols = List.of(
            "AAPL",
            "MSFT",
            "NVDA",
            "AMZN",
            "GOOGL",
            "BE",
            "META",
            "EOSE",
            "LLY",
            "AVGO",
            "TSLA",
            "JPM",
            "V",
            "UNH",
            "OKLO",
            "WMT",
            "MU",
            "COST",
            "LITE",
            "HD",
            "NFLX",
            "CRM",
            "DIS",
            "PEP",
            "ABBV",
            "ORCL",
            "ASTS",
            "CRM",
            "AMD",
            "PLTR"
    );

    @Scheduled(cron = "0 */15 * * * *")
    public void fetchNews() {
        List<String> randomSymbols = new ArrayList<>(symbols);
        Collections.shuffle(randomSymbols);
        List<String> selectedSymbols = randomSymbols.stream().limit(10).toList();

        for (String symbol : selectedSymbols) {
            List<FinnhubNewsDto> news = finnhubService.getCompanyNews(symbol);
            for (FinnhubNewsDto item : news) {
                boolean exists = repository.existsByUrl(item.getUrl());
                if (exists) continue;

                StockNews entity = new StockNews();
                entity.setSymbol(symbol);
                entity.setHeadline(item.getHeadline());
                entity.setSummary(item.getSummary());
                entity.setImage(item.getImage());
                entity.setUrl(item.getUrl());
                entity.setSource(item.getSource());
                entity.setDatetime(item.getDatetime());
                repository.save(entity);
            }
        }
    }
}
