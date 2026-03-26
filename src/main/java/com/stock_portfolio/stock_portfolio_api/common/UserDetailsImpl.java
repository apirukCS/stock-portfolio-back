package com.stock_portfolio.stock_portfolio_api.common;

import com.stock_portfolio.stock_portfolio_api.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String email;

    public UserDetailsImpl(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(
                user.getId(),
                user.getEmail()
        );
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return email; // 👈 ใช้ email เป็น username
    }

    @Override
    public String getPassword() {
        return ""; // 👈 ไม่มี password ก็ใส่ค่าว่าง
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // 👈 ไม่มี role ก็ empty
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
