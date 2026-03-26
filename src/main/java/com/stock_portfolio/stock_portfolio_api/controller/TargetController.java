package com.stock_portfolio.stock_portfolio_api.controller;

import com.stock_portfolio.stock_portfolio_api.common.JwtUtil;
import com.stock_portfolio.stock_portfolio_api.dto.request.TargetRequest;
import com.stock_portfolio.stock_portfolio_api.dto.response.TargetResponse;
import com.stock_portfolio.stock_portfolio_api.entity.Target;
import com.stock_portfolio.stock_portfolio_api.service.TargetService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/targets")
@RequiredArgsConstructor
public class TargetController {

    private final TargetService targetService;
    private final JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<TargetResponse> getTarget(HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromHeaderRequest(request);
        return ResponseEntity.ok(targetService.getByUserId(userId));
    }

    @PutMapping
    public ResponseEntity<Target> updateTarget(HttpServletRequest request, @RequestBody TargetRequest body) {
        Long userId = jwtUtil.getUserIdFromHeaderRequest(request);
        Target target = targetService.updateTarget(userId, body);
        return ResponseEntity.ok(target);
    }
}
