package com.stock_portfolio.stock_portfolio_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/exchange-rate")
@RequiredArgsConstructor
public class ExChangeRateController {
    @GetMapping
    public ResponseEntity<?> getUsdThb(@RequestParam String startPeriod, @RequestParam String endPeriod) {
        RestTemplate rest = new RestTemplate();
        String url = "https://gateway.api.bot.or.th/Stat-ExchangeRate/v2/DAILY_AVG_EXG_RATE/?start_period=" + startPeriod + "&end_period=" + endPeriod;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "eyJvcmciOiI2NzM1NzgwZWM4YzFlYjAwMDEyYTM3NzEiLCJpZCI6ImY3OTY5OTNhMzRiMTQ3NzhhNWZmOGU5YzYzMGU5Mjc1IiwiaCI6Im11cm11cjEyOCJ9");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = rest.exchange(url, HttpMethod.GET, entity, String.class);

        return ResponseEntity.ok(response.getBody());
    }
}
