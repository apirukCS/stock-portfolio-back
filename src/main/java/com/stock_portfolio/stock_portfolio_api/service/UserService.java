package com.stock_portfolio.stock_portfolio_api.service;

import com.stock_portfolio.stock_portfolio_api.dto.request.RegisterRequest;
import com.stock_portfolio.stock_portfolio_api.dto.request.TargetRequest;
import com.stock_portfolio.stock_portfolio_api.dto.response.UserResponse;
import com.stock_portfolio.stock_portfolio_api.entity.User;
import com.stock_portfolio.stock_portfolio_api.exception.BadRequestException;
import com.stock_portfolio.stock_portfolio_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    TargetService targetService;

    public long getCountUser(){
        return userRepository.count();
    }

//    public User createUser(RegisterRequest request) {
//        if (userRepository.existsByUsername(request.getUsername())) {
//            throw new BadRequestException("ชื่อผู้ใช้งานนี้ถูกใช้งานแล้ว");
//        }
//        if (userRepository.existsByEmail(request.getEmail())) {
//            throw new BadRequestException("อีเมลนี้ถูกใช้งานแล้ว");
//        }
//
//        User user = new User();
//        user.setUsername(request.getUsername());
//        user.setEmail(request.getEmail());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        User newUser = userRepository.save(user);
//        //สร้าง target default
//        TargetRequest targetRequest = new TargetRequest();
//        targetRequest.setTarget("");
//        targetService.createTarget(newUser.getId(), targetRequest);
//
//        return newUser;
//    }

//    public List<UserResponse> getAllUsers() {
//        return userRepository.findAll().stream()
//                .map(this::mapToResponse)
//                .collect(Collectors.toList());
//    }
//
//    public UserResponse getUserById(Long id) {
//        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
//        return mapToResponse(user);
//    }

//    private UserResponse mapToResponse(User user) {
//        UserResponse response = new UserResponse();
//        response.setId(user.getId());
//        response.setUsername(user.getUsername());
//        response.setEmail(user.getEmail());
//        return response;
//    }
}

