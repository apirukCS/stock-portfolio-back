package com.stock_portfolio.stock_portfolio_api.controller;

import com.stock_portfolio.stock_portfolio_api.dto.request.LoginRequest;
import com.stock_portfolio.stock_portfolio_api.dto.request.RegisterRequest;
import com.stock_portfolio.stock_portfolio_api.dto.response.LoginResponse;
import com.stock_portfolio.stock_portfolio_api.dto.response.UserInfo;
import com.stock_portfolio.stock_portfolio_api.dto.response.UserResponse;
import com.stock_portfolio.stock_portfolio_api.entity.User;
import com.stock_portfolio.stock_portfolio_api.exception.BadRequestException;
import com.stock_portfolio.stock_portfolio_api.exception.InternalServerException;
import com.stock_portfolio.stock_portfolio_api.repository.UserRepository;
import com.stock_portfolio.stock_portfolio_api.common.JwtUtil;
import com.stock_portfolio.stock_portfolio_api.service.AuthService;
import com.stock_portfolio.stock_portfolio_api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtil jwtUtils;
    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @PostMapping("/google-login")
    public ResponseEntity<LoginResponse> googleLogin(@RequestBody Map<String, String> body) throws Exception {
        String idToken = body.get("idToken");
        return ResponseEntity.ok(authService.loginWithGoogle(idToken));
    }

//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest user) {
//        try {
//            LoginResponse response = auth(user.getUsername(), user.getPassword());
//            return ResponseEntity.status(HttpStatus.OK).body(response);
//        } catch (Exception e) {
//            throw new BadRequestException("ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง");
//        }
//    }

//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(HttpServletRequest request) {
//        String accessToken = request.getHeader("Authorization");
//        if (accessToken != null) {
//            jwtUtils.addToBlacklist(accessToken, jwtUtils.getUsernameFromToken(accessToken));
//        }
//
//        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
//    }

//    @PostMapping("/register")
//    public ResponseEntity<LoginResponse> registerUser(@Valid @RequestBody RegisterRequest user) {
//        User userCreated = userService.createUser(user);
//        LoginResponse response = auth(userCreated.getUsername(), user.getPassword());
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }

//    private LoginResponse auth(String username, String password){
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        Optional<User> dbUser = userRepository.findByUsername(userDetails.getUsername());
//        Long userId = dbUser.map(User::getId).orElse(null);
//
//        LoginResponse response = new LoginResponse();
//        response.setUserId(userId);
//        response.setEmail(userDetails.getUsername());
//        response.setAccessToken(jwtUtils.generateToken(userDetails.getUsername(), userId));
//        return response;
//    }
}
