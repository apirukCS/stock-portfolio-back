package com.stock_portfolio.stock_portfolio_api.service;

import com.nimbusds.jwt.JWTClaimsSet;
import com.stock_portfolio.stock_portfolio_api.common.JwtUtil;
import com.stock_portfolio.stock_portfolio_api.dto.request.TargetRequest;
import com.stock_portfolio.stock_portfolio_api.dto.response.LoginResponse;
import com.stock_portfolio.stock_portfolio_api.entity.User;
import com.stock_portfolio.stock_portfolio_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GoogleTokenVerifier googleTokenVerifier;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtils;
    private final TargetService targetService;

    public LoginResponse loginWithGoogle(String idToken) throws Exception {

        // 1. verify token
        JWTClaimsSet claims = googleTokenVerifier.verify(idToken);

        String email = claims.getStringClaim("email");
//        String googleId = claims.getSubject();
        String name = claims.getStringClaim("name");

        // 2. หา user
        Optional<User> userOpt = userRepository.findByEmail(email);

        User user;

        if (userOpt.isPresent()) {
            user = userOpt.get();
        } else {
            user = new User();
            user.setEmail(email);
            user.setName(name);
            user = userRepository.save(user);
            TargetRequest targetRequest = new TargetRequest();
            targetRequest.setTarget("");
            targetService.createTarget(user.getId(),targetRequest);
        }

        // 3. generate JWT (reuse ของคุณ)
        return generateLoginResponse(user);
    }

    private LoginResponse generateLoginResponse(User user) {
        LoginResponse response = new LoginResponse();
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        response.setAccessToken(jwtUtils.generateToken(user.getEmail(), user.getId()));
        return response;
    }
}
