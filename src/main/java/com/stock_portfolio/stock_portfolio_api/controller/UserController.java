package com.stock_portfolio.stock_portfolio_api.controller;

import com.stock_portfolio.stock_portfolio_api.dto.request.RegisterRequest;
import com.stock_portfolio.stock_portfolio_api.dto.response.UserResponse;
import com.stock_portfolio.stock_portfolio_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/count")
    public Map<String, Long> getCountUser() {
        return Map.of("count", userService.getCountUser());
    }

//    @GetMapping
//    public ResponseEntity<List<UserResponse>> getAllUsers() {
//        return ResponseEntity.ok(userService.getAllUsers());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
//        return ResponseEntity.ok(userService.getUserById(id));
//    }
}

