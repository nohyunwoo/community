package com.example.community.security;

import com.example.community.entity.User;
import com.example.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<User> byUsername = userRepository.findByUsername(username);
        if(byUsername.isEmpty()){
            throw new UsernameNotFoundException("아이디가 존재하지 않음");
        }

        User user = byUsername.get();
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("일반유저"));

        CustomUserDetails customUserDetails = new CustomUserDetails(user.getUsername(), user.getPassword(), authorities);
        customUserDetails.displayName = user.getDisplayName();
        customUserDetails.id = user.getId();

        return customUserDetails;
    }

}
