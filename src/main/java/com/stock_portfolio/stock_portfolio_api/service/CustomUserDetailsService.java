package com.stock_portfolio.stock_portfolio_api.service;

import com.stock_portfolio.stock_portfolio_api.common.UserDetailsImpl;
import com.stock_portfolio.stock_portfolio_api.entity.User;
import com.stock_portfolio.stock_portfolio_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService  implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return UserDetailsImpl.build(user);
    }
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User Not Found with username: " + username);
//        }
//        return new org.springframework.security.core.userdetails.User(
//                user.get().getUsername(),
//                user.get().getPassword(),
//                Collections.emptyList()
//        );
//    }
}
