package com.example.community.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
    public String displayName;
    public Long id;

    public CustomUserDetails(String username, String password,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
