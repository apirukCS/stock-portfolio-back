package com.stock_portfolio.stock_portfolio_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    @GetMapping("/version")
    public String version() {
        return java.time.LocalDateTime.now().toString();
    }
}
