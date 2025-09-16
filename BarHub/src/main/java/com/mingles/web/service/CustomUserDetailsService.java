package com.mingles.web.service;

import com.mingles.web.entity.UserEntity;
import com.mingles.web.model.UserInfoDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userWithRoles = userService.getUserByUsername(username);
        return new UserInfoDetails(userWithRoles);
    }
}
